import json
import requests
import base64
import face_recognition
import b64_img
import cv2
ip = "10.10.0.186"
porta = "8081"
event_id = "7ffeb450-c571-4bfe-95cc-362b744d8e06"

def get_account_info(headers,event_id):
    url = 'http://'+ip+':'+porta+'/event/'+event_id
    #api_url = '{0}account'.format(api_url_base)
    try:
        response = requests.get(url, headers=headers)
        print(response.status_code)
        if response.status_code == 200:
            return json.loads(response.content.decode('utf-8'))
        else:
            return None
    except:
        print('get_account_info, erro na url:',url)


def send_confirm_buy(event_id,headers,valor_pagar):
    
    url = 'http://'+ip+':'+porta+'/cashier/'
    url = url+"/"+event_id+"?amount="+valor_pagar+"&description=123123"
    try:
        r = requests.post(url, data = {},headers=headers)
        return r
    except:
        print('send_confirm_buy, erro na url:',url)
    return False


def load_img_from_Database():
    custumers_encoing_img_database = list()
    print("load from database")
    global imgs_database
    global event_id
    headers = {'Content-Type': 'application/json','Master-Key': 'facefyiscool'}
    json_data = get_account_info(headers=headers,event_id=event_id)
    for customers in json_data['customers']:
        img_base64 = b64_img.readb64(customers['base64Photo'])
        if(img_base64 is not None):
            small_img_detect = cv2.resize(img_base64, (0, 0), fx=float(1/2), fy=float(1/2))
            face_encoding = face_recognition.face_encodings(small_img_detect)
            if(len(face_encoding)>0):
                custumers_encoing_img_database.append({'id':customers['customerId'],'b64':face_encoding[0],'nome':customers['firstName']})
            else:
                print(customers['customerId'],' face nao detectada')
        else:
            print(customers['customerId'],' nao tem foto correta')
    print("finish load from database")
    return custumers_encoing_img_database

# event_id = "7ffeb450-c571-4bfe-95cc-362b744d8e06"
# headers = {'Content-Type': 'application/json','Master-Key': 'facefyiscool'}
# print(get_account_info(headers,event_id))

# id_do_carinha = 1
# headers= {'Custumer-Id':id_do_carinha,'Content-Type':'application/json'}
# send_confirm_buy(event_id,headers)