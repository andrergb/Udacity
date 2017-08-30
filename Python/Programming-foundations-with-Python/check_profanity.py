import urllib

def read_text():
    quotes = open("C:\UDACITY\PYTHON\movies_quotes.txt")
    contents_of_file = quotes.read()
    quotes.close()
    return contents_of_file

def check_profanity(text_to_check):
    connection = urllib.urlopen("http://www.wdylike.appspot.com/?q=" + text_to_check)
    output = connection.read()
    if "true" in output:
        print "Proganity Alert!"
    elif "false" in output:
        print "This document has no curse words!"
    else:
        print "Could not scan the document properly."
    connection.close()

text = read_text()
check_profanity(text)
