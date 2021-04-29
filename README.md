- Voice recognition?
- At this time of year?
- At this time of day?
- In this part of the country?
- Localized, entirely within your phone?

Yes, and you may see it:

# VOSK4Tasker

VOSK4Tasker uses [Alpha Cephei's Vosk](https://alphacephei.com/vosk) speech recognition toolkit, and
glues it for use with Tasker.

## Downloads

Download at your own risk. This is not fully featured: https://ebc.li/tasker/vosk/

The APK is ~50 MBs. Be careful with your data plan!

## Building

1. Run `./download-model.sh`, or manually do what it does if you're not under Linux
2. Build under Android Studio
3. profit

## Usage

After installation, Tasker should recognize this and give you an option to add a new action from the
"Plugin" menu. In the action, set "Prompt" to what you want the user to see when asked for speech
input, and set "Event ID" to something unique.

To get the speech as text back to Tasker, create an event for "Text-to-speech Spoken" under the
"Plugin" menu. Set the "Event ID" to the same unique string you set, and continue execution from
there!

This is unwieldy, I know, but due to some current limitations this has to work like this. ~~Please
fork the code and fix all the bugs yourself i am done with android development it's a pain in the-~~
