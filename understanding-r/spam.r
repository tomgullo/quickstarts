sms_raw <- read.csv("sms_spam.csv", stringsAsFactors = FALSE)
str(sms_raw)

sms_raw$type <- factor(sms_raw$type)

table(sms_raw$type)
