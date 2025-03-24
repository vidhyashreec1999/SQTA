// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.imagetextextractor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/api"})
public class ImageTextController {
   public ImageTextController() {
   }

   @PostMapping({"/extract-text"})
   public String extractTextFromImage(@RequestParam("file") MultipartFile file) {
      try {
         File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
         file.transferTo(convFile);
         BufferedImage image = ImageIO.read(convFile);
         BufferedImage processedImage = this.preprocessImage(image);
         File processedFile = new File(System.getProperty("java.io.tmpdir") + "/processed_" + file.getOriginalFilename());
         ImageIO.write(processedImage, "jpeg", processedFile);
         Tesseract tesseract = new Tesseract();
         tesseract.setDatapath("C://Program Files//Tesseract-OCR//tessdata");
         tesseract.setLanguage("kan+san+eng");
         String text = tesseract.doOCR(processedFile);
         return text;
      } catch (IOException | TesseractException var8) {
         var8.printStackTrace();
         return "Error occurred while extracting text from image.";
      }
   }

   private BufferedImage preprocessImage(BufferedImage image) {
      return image;
   }
}
