import paho.mqtt.client as mqtt
import json
import random

def success_connect(*args):
    print('连接成功')

def on_message(client, other, message: mqtt.MQTTMessage):
    print(message.topic)
    print(message.payload)
    print(json.loads(message.payload))
    data = json.loads(message.payload)
    request_id = data.get('request_id', 'none')
    data = {
            'request_id': request_id,
            'errcode': 2002,
            'errmsg': 'params incorrect',
            'data': {
            'ok': 'success'
            }
        }
    client.publish('/pub/product/1998/1222222222/set', json.dumps(data, ensure_ascii=False))
    print('发送成功')

def on_subscribe(*args):
    print('订阅成功')

client = mqtt.Client(client_id='test_' + str(random.randint(1, 1000000000000)))
client.on_connect = success_connect
client.on_message = on_message
client.on_subscribe = on_subscribe
# client.username_pw_set('admin', 'admin')
client.connect("114.55.104.50", 1883, 60)
client.subscribe('/sub/product/1998/1222222222/set')
# while 1:
# time.sleep(1)
# client.publish('/sub/product/1/1222222222/set', "sdsdsd")
client.loop_forever()
