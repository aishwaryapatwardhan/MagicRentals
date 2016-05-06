Google Signin  - Reference - Rekha
*************************************

To get production key

keytool -exportcert -list -v -alias <your-key-name> -keystore <path-to-production-keystore>


TO get Debug fingerprint key

keytool -exportcert -list -v -alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore