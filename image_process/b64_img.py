import base64
import numpy as np
import cv2
import os
import time
from imageio import imread
import io
from PIL import Image
from io import StringIO
import numpy as np


def img_path_to_b64(img_path):
    with open(img_path, "rb") as imageFile:
        str_64 = base64.b64encode(imageFile.read())
        return str_64



def img_to_b64(img):
    str_64 = base64.b64encode(img)
    return str_64


def readb64(encoded_data):
   nparr = np.fromstring(base64.b64decode(encoded_data), np.uint8)
   img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
   return img
   cv2.imwrite("temp.png",img)
   time.sleep(0.01)
   img = cv2.imread("temp.png",1)
   print(img)
   return img
    

def testes():
    str1 = 'R0lGODlhEAAQAMQAAORHHOVSKudfOulrSOp3WOyDZu6QdvCchPGolfO0o/XBs/fNwfjZ0frl3/zy7////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAABAALAAAAAAQABAAAAVVICSOZGlCQAosJ6mu7fiyZeKqNKToQGDsM8hBADgUXoGAiqhSvp5QAnQKGIgUhwFUYLCVDFCrKUE1lBavAViFIDlTImbKC5Gm2hB0SlBCBMQiB0UjIQA7'
    string_64 = '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'
    cv2.imshow('teste', readb64(string_64))
