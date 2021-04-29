- Voice recognition?
- At this time of year?
- At this time of day?
- In this part of the country?
- Localized, entirely within your phone?

Yes, and you may see it:

# VOSK4Tasker

VOSK4Tasker uses [Alpha Cephei's Vosk](https://alphacephei.com/vosk) speech recognition toolkit, and
glues it for use with Tasker.

## [Downloads](https://github.com/Admicos/VOSK4Tasker/releases)

The APK is ~50 MBs. Be careful with your data plan!

## Building

1. Run `./scripts/english.sh` (change `english` with a different language if exists)
2. Build under Android Studio
3. profit

### Manual language model download

If you're not under Linux, or just want to download a different model, here's how to download the
models manually.

1. Go to https://alphacephei.com/vosk/models
2. Pick a small model (example: `vosk-model-small-tr-0.3`)
3. Download zip and extract
4. Rename the folder inside zip to `model`
5. Place it as `app/src/main/assets/model`

## Usage

After installation, Tasker should recognize this and give you an option to add a new action from the
"Plugin" menu. In the action, set "Prompt" to what you want the user to see when asked for speech
input, and you're done. The variable `%text` is your speech to text output.