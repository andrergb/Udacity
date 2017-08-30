import time
import os

print "Secret Message started on " + time.ctime()

def rename_files():
    file_list = os.listdir(cur_dir)
    print file_list

    saved_path = os.getcwd()
    
    os.chdir(cur_dir)
    for file_name in file_list:
        print("Old name - " + file_name)
        print("New name - " + file_name.translate(None, "0123456789"))
        os.rename(file_name, file_name.translate(None, "0123456789"))

    os.chdir(saved_path)
    
cur_dir = r"C:\UDACITY\PYTHON\prank"
rename_files()

print "Secret Message ended on " + time.ctime()
