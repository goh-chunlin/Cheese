# Cheese
This is an Android app demonstrating how Android Speech Recognizer and Microsoft Cognitive Services on Azure
work together to helps translating your speech from one language (Chinese is used as primary language
in this app) to another.

Travelling overseas is an adventurous activity, but we at the same time also worry about the unknown
especially when you are visiting countries where they don't speak the same language as you. Hence,
this application helps to do speech-to-speech translation in some of the popular languages.

## Project Objectives
 - Understand how to implement [Android Speech Recognizer](https://developer.android.com/reference/android/speech/SpeechRecognizer.html);
 - Learn LUIS - [Language Understanding Intelligence Service](https://www.luis.ai/) from MS Cognitive Services;
 - Play with [Japanese VoiceText API](http://voicetext.jp/) which allows emotions in the system generated voices.

## Technologies Used
 - Android Speech Recognizer
 - LUIS (Language Understanding Intelligence Service)
 - Google Translate
 - VoiceText Web API
 - Android TTS (TextToSpeech)


## Fields to Customize
4 API keys in apikeys.xml are needed before you can run and test this application.
 - MICROSOFT_COGNITIVE_ID: [Getting Started with LUIS](https://www.microsoft.com/cognitive-services/en-us/LUIS-api/documentation/GetStartedWithLUIS-Basics)
 - MICROSOFT_COGNITIVE_SUBSCRIPTION_KEY: Read [Creating Subscription Keys via Microsoft Azure](https://www.microsoft.com/cognitive-services/en-us/LUIS-api/documentation/AzureIbizaSubscription)
 - GOOGLE_TRANSLATE_API_KEY: [Google Translate API](https://cloud.google.com/translate/v2/quickstart)
 - JAPANESE_VOICE_TEXT_API: [VoiceText Web API](https://cloud.voicetext.jp/webapi)


## References
 - [Android-ListDialogExample](https://github.com/ruben-h/Android-ListDialogExample)