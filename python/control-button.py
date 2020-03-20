import RPi.GPIO as GPIO
import time
import json
import requests
import pprint
import logging

logging.basicConfig(filename='controlbutton.log',level=logging.DEBUG)

logging.debug('Starting controlbutton application')

URL = 'http://localhost:8080/api/v1/lights'
BUTTON_1 = 18
BUTTON_2 = 23  
BUTTON_3 = 24
BUTTON_4 = 21 

GPIO.setmode(GPIO.BCM)

GPIO.setup(BUTTON_1, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(BUTTON_2, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(BUTTON_3, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(BUTTON_4, GPIO.IN, pull_up_down=GPIO.PUD_UP)

def doPost( mode ):
   requestUrl = URL + '/' + mode
   data = {"active":"true"}
   data_json = json.dumps(data)
   headers = {'Content-type': 'application/json'}
   response = requests.post(requestUrl, data=data_json, headers=headers)

   print('Made request to ' + requestUrl)
   logging.debug('Made request to ' + requestUrl)
   return

def checkState(button, mode):
   btn_state = GPIO.input(button)

   if btn_state == True:
       logging.debug('Button Pressed')
       print('Button Pressed')
       doPost(mode);
       time.sleep(0.2)

while True:
    checkState(BUTTON_1, 'off')
    checkState(BUTTON_2, 'mode1')
    checkState(BUTTON_3, 'mode2')
    checkState(BUTTON_4, 'mode3')
