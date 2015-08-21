# Import smtplib for the actual sending function
import smtplib

import mimetypes
from email.mime.multipart import MIMEMultipart
from email import encoders
from email.message import Message
from email.mime.audio import MIMEAudio
from email.mime.base import MIMEBase
from email.mime.image import MIMEImage
from email.mime.text import MIMEText

from email.mime.multipart import MIMEMultipart

# Import the email modules we'll need
from email.mime.text import MIMEText


# Open a plain text file for reading.  For this example, assume that
# the text file contains only ASCII characters.
textfile = "testfile.txt"
fp = open(textfile, 'rb')
# Create a text/plain message
msg = MIMEMultipart(fp.read())
fp.close()

me = "testfrom@fake.com"
you = "testto@fake.com"
msg['Subject'] = 'The contents of %s' % textfile
msg['From'] = me
msg['To'] = you

ctype, encoding = mimetypes.guess_type(textfile)
if ctype is None or encoding is not None:
 ctype = "application/octet-stream"

maintype, subtype = ctype.split("/", 1)

fp = open(textfile)
# Note: we should handle calculating the charset
attachment = MIMEText(fp.read(), _subtype=subtype)
fp.close()

attachment.add_header("Content-Disposition", "attachment", filename=textfile)
msg.attach(attachment)

# Send the message via our own SMTP server, but don't include the
# envelope header.
s = smtplib.SMTP('localhost', '5050')
#s.starttls()
s.sendmail(me, [you], msg.as_string())
s.quit()
