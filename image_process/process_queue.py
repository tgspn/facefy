import face_recognition
import cv2
import sys
import threading
import multiprocessing
import time
import os
from multiprocessing import Pool
import api
import json

#parametros
velocidade_proc = 2
path= "imgs/queue/"
event_id = "7ffeb450-c571-4bfe-95cc-362b744d8e06"

#variavel global
custumers_encoing_img_database = list()

def match_faces(img_full_path): 
    global custumers_encoing_img_database
    print(img_full_path)
    img = cv2.imread(img_full_path,1)
    small_img = cv2.resize(img, (0, 0), fx=float(1/velocidade_proc), fy=float(1/velocidade_proc))
    face_locations = face_recognition.face_locations(small_img)
    face_encodings = face_recognition.face_encodings(small_img, face_locations)
    for face_encoding in face_encodings:
        for img_encoing_database in custumers_encoing_img_database:
            matche = face_recognition.compare_faces([img_encoing_database['b64']], face_encoding)
            print(matche)
    os.remove(img_full_path)


def load_img_from_Database():
    print("load from database")
    global imgs_database
    global event_id
    headers = {'Content-Type': 'application/json','Master-Key': 'facefyiscool'}
    json_data = api.get_account_info(headers=headers,event_id=event_id)
    print('customers')
    for customers in json_data['customers']:
        custumers_encoing_img_database.append({'id':customers['customerId'],'b64':customers['base64Photo']})
        print(customers['customerId'],customers['base64Photo'])
    #fake data
    # for i in range(0,100):
    #     dhiogo_image = face_recognition.load_image_file("dhiogo.jpg")
    #     small_dhiogo = cv2.resize(dhiogo_image, (0, 0), fx=float(1/4), fy=float(1/4))
    #     dhiogo_face_encoding = face_recognition.face_encodings(small_dhiogo)[0]
    #     imgs_database.append(dhiogo_face_encoding)
    print("finish load from database")


def infinit_process():
    print("start infinit process...")
    while(True):
        if(len(os.listdir(path))==0):
            time.sleep(3)
            continue
        onlyfiles = [os.path.join(path, f) for f in os.listdir(path) ]
        time.sleep(3)
        p = Pool()
        p.map(match_faces, onlyfiles)
        p.close()
        p.join()



load_img_from_Database()
infinit_process()