import RPi.GPIO as GPIO
import time
import pyrebase

GPIO.setmode(GPIO.BCM)

RED_PIN = 17
FLAME_PIN = 23

config = {
    "apiKey": "AIzaSyCWYL1nqsrug3kGGuigzE5TEJLSoO7mWgc",
    "authDomain": "degi76-e7d8a.firebaseapp.com",
    "databaseURL": "https://degi76-e7d8a-default-rtdb.firebaseio.com",
    "storageBucket": "degi76-e7d8a.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()

GPIO.setup(FLAME_PIN, GPIO.IN)
GPIO.setup(RED_PIN, GPIO.OUT)

try:
	while True:
		flame_state = GPIO.input(FLAME_PIN)

		if flame_state == GPIO.HIGH:
			GPIO.output(RED_PIN, GPIO.HIGH)
			message = "Fire, RUN!"
			print (message)
			db.child("messages").push(message)
			time.sleep(2)
		else:
			GPIO.output(RED_PIN, GPIO.LOW)
			message="No fire, chill"
			print(message)
			db.child("messages").push(message)
			time.sleep(2)
            
except KeyboardInterrupt:
	GPIO.cleanup()