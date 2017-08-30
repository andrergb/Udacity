import time
import webbrowser

print "Take a break started on " + time.ctime()

max_breakes = 3
count = 0
while (count < max_breakes):
    time.sleep(10)
    webbrowser.open("http://www.google.com")
    count = count + 1
    
print "Ended at " + time.ctime()
