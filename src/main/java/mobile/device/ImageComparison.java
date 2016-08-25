package mobile.device;

import org.apache.http.annotation.Experimental;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

public class ImageComparison {

    /*
    @Test(dataProvider = "search")
    public void eventsSearch(String data) throws InterruptedException, IOException {
        Thread.sleep(2000);
        boolean status = false;
        //take screen shot
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // for (String event : events) {
        driver.findElementById("your id").sendKeys(data);
        driver.hideKeyboard();
        List<WebElement> list = driver.findElementsByXPath("//*[@class='android.widget.TextView' and @index='1']");
        System.out.println(list.size());

        int i = 0;
        for (WebElement el : list) {
            String eventList = el.getText();
            System.out.println("events" + " " + eventList);
            if (eventList.equals("gg")) {
                status = true;
                break;
            }
            i++;
        }

        //capture image of searched contact icon
        List<WebElement> imageList = driver.findElementsByXPath("//*[@class='android.widget.ImageView' and @index='0']");
        System.out.println(imageList.size());

        System.out.println(i);
        WebElement image = imageList.get(1);
        Point point = image.getLocation();
        //get element dimension
        int width = image.getSize().getWidth();
        int height = image.getSize().getHeight();
        BufferedImage img = ImageIO.read(screen);
        BufferedImage dest = img.getSubimage(point.getX(), point.getY(), width, height);
        ImageIO.write(dest, "png", screen);
        File file = new File("Menu.png");
        FileUtils.copyFile(screen, file);

        //verify images
        verifyImage("Menu.png", "Menu.png");
        //Assert.assertTrue(status, "FAIL Event doesn't match" + data);
    }*/

    /***
     * Method to assert image comparison to benchmark screens
     *
     * @param benchmarkImagePath path of the benchmark .png screenshot
     * @param currentImagePath   path of the .png screenshot captured
     * @throws IOException for i/o handling
     */
    @Experimental
    private void verifyImage(String benchmarkImagePath, String currentImagePath) throws IOException {
        File fileInput = new File(benchmarkImagePath);
        File fileOutPut = new File(currentImagePath);

        BufferedImage bufferedImage = ImageIO.read(fileInput);
        DataBuffer dataBuffer = bufferedImage.getData().getDataBuffer();
        int sizeFileInput = dataBuffer.getSize();

        BufferedImage bufferedImageFinal = ImageIO.read(fileOutPut);
        DataBuffer dataBufferFinal = bufferedImageFinal.getData().getDataBuffer();
        int sizeFileOutPut = dataBufferFinal.getSize();

        boolean matchFlag = true;

        if (sizeFileInput == sizeFileOutPut) {
            for (int j = 0; j < sizeFileInput; j++) {
                if (dataBuffer.getElem(j) != dataBufferFinal.getElem(j)) {
                    matchFlag = false;
                    break;
                }
            }
        } else matchFlag = false;
        Assert.assertTrue(matchFlag, "Images are not same");
    }
}
