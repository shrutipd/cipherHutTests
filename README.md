1) Need the latest chrome driver intsatlled in c drive "c:/chromedriver_win32/chromedriver.exe". My chrome version was 73.0. Install it from this link:http://chromedriver.chromium.org/downloads

2)Go to this URL and enable allow access to less secure apps https://myaccount.google.com/lesssecureapps?pli=1

3)Place the email.properties in the same folder in which the jar is present. Change the email address and password.

4)For balance comparison on the funds page, ActualBalance.csv is generated at run time with actual data and is compared with ExpectedBalance.csv . Please specify the expected data in  ExpectedBalance.csv file, you can specify all lines or just the line you want to verify based on your need.For reference I have created ExpectedBalance.csv and one ExpectedBalance2.csv. 
Always the filename with expected data must be ExpectedBalance.csv.If ExpectedBalance.csv is not present then the test checkBalance will fail.

5)You can delete generated files(csv pairs) having file name BTC.csv, CBNX.csv, EUR.csv, TRY.csv and USDT.csv.
