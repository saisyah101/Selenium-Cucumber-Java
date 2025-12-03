package stepDefinitions.base;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static driver.DriverFactory.cleanupDriver;
import static driver.DriverFactory.getDriver;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class Hooks {
    private ScreenRecorder screenRecorder;

    private static final boolean ENABLE_VIDEO_RECORDING = false;

    @Before
    public void setup(Scenario scenario) throws Exception {
        getDriver();

        if (ENABLE_VIDEO_RECORDING) {
            startRecording(scenario);
        }
    }

    @AfterStep
    public void captureExceptionImage(Scenario scenario){
        if (scenario.isFailed()){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeMilliseconds = Long.toString(timestamp.getTime());

            byte[] screenshot = ((TakesScreenshot) getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", timeMilliseconds);
        }
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        if (ENABLE_VIDEO_RECORDING) {
            System.out.println("Stopping recording");
            Thread.sleep(1000);
            stopRecording(scenario);
        }

        System.out.println("Cleaning up driver");
        cleanupDriver();
    }

    private void startRecording(Scenario scenario) throws Exception {
        File file = new File("./test-recordings/");

        if (!file.exists()) {
            file.mkdirs();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        screenRecorder = new ScreenRecorder(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),

                new Format(MediaTypeKey, MediaType.VIDEO,
                        EncodingKey, ENCODING_AVI_MJPG,
                        CompressorNameKey, ENCODING_AVI_MJPG,
                        DepthKey, 24,
                        FrameRateKey, Rational.valueOf(10),
                        QualityKey, 0.8f,
                        KeyFrameIntervalKey, 10 * 60),

                new Format(MediaTypeKey, MediaType.VIDEO,
                        EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(10)),

                null, file);

        screenRecorder.start();
        System.out.println("Recording started for: " + scenario.getName());
    }

    private void stopRecording(Scenario scenario) throws Exception {
        if (screenRecorder != null) {
            screenRecorder.stop();

            File aviFile = screenRecorder.getCreatedMovieFiles().get(0);
            String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String webmFileName = scenarioName + "_" + timestamp + ".webm";
            File webmFile = new File(aviFile.getParent(), webmFileName);

            System.out.println("Converting video to WebM");
            convertToWebM(aviFile, webmFile);
        }
    }

    private void convertToWebM(File inputFile, File outputFile) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFile.getAbsolutePath(),

                    "-c:v", "libvpx-vp9",
                    "-crf", "32",
                    "-b:v", "0",

                    "-cpu-used", "4",
                    "-threads", "0",

                    "-deadline", "good",
                    "-row-mt", "1",

                    "-auto-alt-ref", "1",
                    "-lag-in-frames", "25",

                    "-y",
                    outputFile.getAbsolutePath()
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("frame=") || line.contains("time=")) {
                    System.out.print("\r" + line);
                }
            }
            System.out.println();

            int exitCode = process.waitFor();

            if (exitCode == 0 && outputFile.exists()) {
                long fileSizeMB = outputFile.length() / (1024 * 1024);
                System.out.println("Video saved: " + outputFile.getName() +
                        " (" + fileSizeMB + " MB)");
                inputFile.delete();
            } else if (exitCode == 0 && !outputFile.exists()) {
                System.err.println("Conversion reported success but output file not found!");
                System.err.println("Expected: " + outputFile.getAbsolutePath());
                System.err.println("Keeping AVI file: " + inputFile.getAbsolutePath());
            } else {
                System.err.println("FFmpeg conversion failed with exit code: " + exitCode);
                System.err.println("Keeping AVI file: " + inputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Failed to convert video to WebM: " + e.getMessage());
            System.err.println("Original AVI file preserved: " + inputFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}