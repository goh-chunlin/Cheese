# Cheese
This is an Android app demonstrating how Android Speech Recognizer and Microsoft Cognitive Services on Azure
work together to helps translating your speech from one language (Chinese is used as primary language
in this app) to another.

| ![Language List](github-images/LanguageList.png?raw=true) | ![Voice Input](github-images/GoogleSpeech.png?raw=true) | ![Translating...](github-images/Translating.png?raw=true) | ![Successfully Translated](github-images/SuccessfullyTranslated.png?raw=true) |
| --- | --- | --- | --- |
| Choosing Language | Voice Input | Translating | Successfully Translated! |

Travelling overseas is an adventurous activity, but we at the same time also worry about the unknown
especially when you are visiting countries where they don't speak the same language as you. Hence,
this application helps to do speech-to-speech translation in some of the popular languages.

My idea of using Microsoft Cognitive Services, especially Language Understanding Intelligence Service (LUIS) comes
from Riza Marhaban's presentation during [Microsoft Developer Day at National University of Singapore (NUS) in May 2016](https://www.microsoft.com/en-sg/MicrosoftDeveloperDay).

![Riza's Presentation about MS Cognitive APIs](github-images/Presentation.jpg?raw=true)
Riza's Presentation about MS Cognitive APIs

## Project Objectives
 - Build a travel companion app which allows me travelling to Hong Kong and Japan alone and chat with the locals there;
 - Understand how to implement [Android Speech Recognizer](https://developer.android.com/reference/android/speech/SpeechRecognizer.html);
 - Learn LUIS - [Language Understanding Intelligence Service](https://www.luis.ai/) from MS Cognitive Services;
 - Play with [Japanese VoiceText API](http://voicetext.jp/) which allows emotions in the system generated voices.

![Using of "Translate" Intent in LUIS](github-images/LUIS.png?raw=true)
Using of "Translate" Intent in LUIS

![VoiceText with Cute Voices from Japan](github-images/VoiceText.png?raw=true)
VoiceText with Cute Voices from Japan

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