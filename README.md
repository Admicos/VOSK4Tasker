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

No downloadable versions exist as this is not complete just yet. If you're curious and have an Android
development environment, read on!

## Building

1. Run `./download-model.sh`, or manually do what it does if you're not under Linux
2. Build under Android Studio
3. profit

## Usage

After installation, Tasker should recognize this and give you an option to add it under a new event.

Currently, this is pretty broken, and the event does not work as well as it should, but if you're
lucky you might get it to recognize your voice for a bit, without returning anything to Tasker.