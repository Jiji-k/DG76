import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
RED_PIN = 17
FLAME_PIN = 23

GPIO.setup(FLAME_PIN, GPIO.IN)
GPIO.setup(RED_PIN, GPIO.OUT)

try:
	while True:
		flame_state = GPIO.input(FLAME_PIN)

		if flame_state == GPIO.HIGH:
			GPIO.output(RED_PIN, GPIO.HIGH)
			print ("Fire, RUN!")
			time.sleep(2)
		else:
			GPIO.output(RED_PIN, GPIO.LOW)
			print("No fire, chill")
			time.sleep(2)
            
except KeyboardInterrupt:
	GPIO.cleanup()