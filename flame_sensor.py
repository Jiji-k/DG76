import RPi.GPIO as GPIO

# Set the GPIO mode and pin number
GPIO.setmode(GPIO.BCM)
flame_pin = 4  # GPIO pin number you have connected the DO pin to

# Set up the GPIO pin as input
GPIO.setup(flame_pin, GPIO.IN)

try:
    while True:
        flame_state = GPIO.input(flame_pin)  # Read the digital state of the pin

        if flame_state == GPIO.HIGH:
            print("Flame detected!")
        else:
            print("No flame detected.")

except KeyboardInterrupt:
    # Clean up the GPIO settings on program exit
    GPIO.cleanup()
