import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

import java.time.LocalDate

/**
 * 用于生成带 Lombok 注释并继承基类的实体类的脚本
 */

// 此处指定包路径，路径需要自行维护;
packageName = "com.xxx.xxx.entity;"
// 此处指定对应的类型映射，可按需修改，目前tinyint如果要映射到自定义枚举类型，只能手动修改
typeMapping = [
        (~/(?i)bigint/)                   : "Long",
        (~/(?i)int/)                      : "Integer",
        (~/(?i)tinyint/)                  : "Boolean",
        (~/(?i)float|double|decimal|real/): "BigDecimal",
        (~/(?i)time|datetime|timestamp/)  : "LocalDateTime",
        (~/(?i)date/)                     : "LocalDate",
        (~/(?i)/)                         : "String"
]

// 上面用到类和它的导入路径的之间的映射
importMap = [
        "BigDecimal" : "java.math.BigDecimal",
        "LocalDate" : "java.time.LocalDate",
        "LocalDateTime" : "java.time.LocalDateTime",
]

// 导入路径列表，下面引用的时候会去重，也可以直接声明成一个 HashSet
importList = []

// 基类中已经有的属性，就无需在新生成的类中声明了
userTimeList = [
        "create_user_id",
        "update_user_id",
        "create_time",
        "update_time"
]

// 弹出选择文件的对话框
FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true) + "PO"
    def writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(dir, className + ".java")), "utf-8"))
    def fields = calcFields(table)
    writer.withPrintWriter { out -> generate(out, className, fields, table) }
}

// 从这里开始，拼实体类的具体逻辑代码
def generate(out, className, fields, table) {
    out.println "package $packageName"
    out.println ""
    out.println ""
    // 引入所需的包
    out.println "import com.xxx.xxx.entity.BaseEntity;"
    out.println "import lombok.Data;"
    out.println "import lombok.EqualsAndHashCode;"
    out.println "import java.io.Serializable;"
    out.println "import javax.persistence.Id;"
    out.println "import javax.persistence.Table;"
    out.println "import javax.persistence.GeneratedValue;"
    // 去重后导入列表
    importList.unique().each() { pkg ->
        out.println "import " + pkg + ";"
    }
    out.println ""
    // 添加类注释
    out.println "/**"
    // 如果添加了表注释，会加到类注释上
    if (isNotEmpty(table.getComment())) {
        out.println " * " + table.getComment()
    }
    out.println " *"
    out.println " * @author xxx"
    out.println " * @date " + LocalDate.now()
    out.println " */"
    // 添加类注解
    out.println "@Data"
    out.println "@EqualsAndHashCode(callSuper = false)"
    out.println "@Table(name = \"${table.getName()}\")"
    out.println "public class $className extends BaseEntity implements Serializable {"
    out.println ""
    boolean isId = true
    // 遍历字段，按下面的规则生成
    fields.each() {
        // 输出注释
        if (isNotEmpty(it.comment)) {
            out.println "\t/**"
            out.println "\t * ${it.comment}"
            out.println "\t */"
        }
        // 这边默认第一个字段为主键，实际情况大多数如此，遇到特殊情况可能需要手动修改
        if (isId) {
            out.println "\t@Id"
            out.println "\t@GeneratedValue(generator = \"JDBC\")"
            isId = false
        }
        out.println "\tprivate ${it.type} ${it.name};"
        out.println ""
    }
    out.println "}"
}

def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        // 如果是BaseTimeUserIdPO中已有的字段，则不再添加
        if (!userTimeList.contains(col.getName())) {
            // 如果是需要导入的类型，则往list里面添加
            if (importMap.containsKey(typeStr)) {
                importList.add(importMap.get(typeStr))
            }
            fields += [[
                               name   : javaName(col.getName(), false),
                               type   : typeStr,
                               dbType : col.getDataType(),
                               comment: col.getComment()
                       ]]
        } else {
            fields += []
        }
    }
}

def isNotEmpty(content) {
    return content != null && content.toString().trim().length() > 0
}

def javaName(str, capitalize) {
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}

