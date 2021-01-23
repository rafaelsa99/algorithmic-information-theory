# Lab Work 3
The objective of the program is to perform the identification of music through small samples, using the Normalized Compression Distance (NCD). <br>
The NCD is calculated based on compressors, and will indicate the degree of similarity between a song and the sample, being that the smaller the value, bigger the degree of similarity.

# Elements

Nome: António Jorge Ferreira Ramos

GitHub: tonyevolutionstar

Email: ajframos@ua.pt

Nº: 101193

<br>

Nome: Luís Carlos Laranjeira Marques

GitHub: LuiisLaranjeira

Email: lclm@ua.pt

Nº: 81526

<br>

Nome: Rafael Gomes de Sá

GitHub: rafaelsa99

Email: rafael.sa@ua.pt

Nº: 104552

## Lab Work 3

### How to run:
To run the program simply run the commands below in the bin directory.


### Requirements:
Java Runtime Environment <br>
The project directory, and files, must have all permissions (read, write and execute).


### Parameters:
- <b>compressionMethod</b>: Compression method to be used when calculating the NCD. Compression methods available: gzip, bzip2, lzma, deflate, lz4, snappy, xz, zstd.<br>
- <b>databaseFolder</b>: Path to the folder that contains the musics or frequencies of the database.<br>
- <b>musicSample</b>: Path to the sample file to be identified.
- <b>noiseLevel</b>: Volume of the noise that will be added to the sample to be identified (0 <= noiseLevel <= 1).
- <b>noiseType</b>: Type of noise that will be added to the sample to be identified. Noise types available: whitenoise, pinknoise or brownnoise.
- <b>topSize</b>: Number of best matches that will be returned.
- <b>-g</b> (Optional): Flag that allows to generate the frequency files of the songs in the database folder.
- <b>samplesFolder</b>: Path to the folder that contains the set of samples to be identified for the accuracy calculation.
- <b>sampleFolder</b>: Path to the folder to where the samples will be generated.
- <b>seconds</b>: The length, in seconds, of the samples that will be generated.

#### Notes:
- To add new musics to the database just put the audio files in the database folder and run the musicIdentification program with the -g flag.
- The audio files should be .wav or .flac, stereo, sampled at 44100 Hz, 16 bits.
- <b>The results obtained through the binaries on Windows and Linux systems vary slightly. The reason for this behavior was not discovered, since the program is executed in the same way in both cases. The results provided were obtained by running the program on Windows.</b>

#### Run musicIdentification:
```
java -jar musicIdentification.jar compressionMethod databaseFolder musicSample noiseLevel noiseType topSize [-g]
```

#### Run precision:
```
java -jar precision.jar compressionMethod databaseFolder samplesFolder noiseLevel noiseType
```

#### Run generateSamples:
```
java -jar generateSamples.jar seconds databaseFolder sampleFolder
```

#### musicIdentification Example:
```
java -jar musicIdentification.jar bzip2 ../audioFiles/ "../Samples/Samples_10sec/Pink Floyd - Comfortably Numb.wav" 0.0 whitenoise 10
```

##### Expected results for the example:
```
Top 10: Music "Avenged Sevenfold - So Far Away" with NCD = 0.9999409750914886
Top 9: Music "David Bowie - Absolute Beginners" with NCD = 0.9998949668095118
Top 8: Music "Aerosmith - Cryin'" with NCD = 0.9997681274636457
Top 7: Music "Peste & Sida - Sol da Caparica" with NCD = 0.9996531133080602
Top 6: Music "Metallica - Nothing Else Matters" with NCD = 0.9996400719856029
Top 5: Music "Tom Walker - Just You and I" with NCD = 0.9995388783215171
Top 4: Music "Tears of The Dragon - Bruce Dickinson" with NCD = 0.9995301790846783
Top 3: Music "Oasis - Wonderwall" with NCD = 0.9992865296803652
Top 2: Music "REM - Everybody Hurts" with NCD = 0.9981267143908477
Top 1: Music "Pink Floyd - Comfortably Numb" with NCD = 0.9949941473862476
Execution time: 34.1754855 seconds
```

#### precision Example:
```
java -jar precision.jar zstd ../audioFiles/ ../Samples/Samples_10sec/ 0.0 whitenoise
```

##### Expected results for the example:
```
Samples with wrong classifications:
        Expected Result: AC DC - Thunderstruck -> Obtained Result: Billy Idol - Rebel Yell

Classification Accuracy: 99.1304347826087%
Execution time: 113.281004301 seconds
```

#### generateSamples Example:
```
java -jar generateSamples.jar 10 ../audioFiles/ ../Samples/Samples_10sec/
```
The program generateSamples does not return any output. The generated files may be checked in the folder indicated as parameter.
