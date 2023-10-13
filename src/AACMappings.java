import structures.AssociativeArray;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Keeps track of the complete set of AAC mappings.
 * 
 * @author Jayson Kunkel
 */
public class AACMappings{

  AACCategory topLevelCategory;// maps images to category names
  AACCategory currentCategory; 
  AssociativeArray<String, AACCategory> categories; // mpas category images to locations

  /**
   * Reads in the file, creates mappings from images to categories, add all items to each category.
   * Starts the AAC on the home screen.
   * 
   * @param filename the file to read in
   */
  public AACMappings(String filename){
    categories = new AssociativeArray<String, AACCategory>();

    // sets toplevelCategory and currentcategory to the home screen
    this.topLevelCategory = new AACCategory("");
    this.currentCategory = topLevelCategory;
    this.currentCategory.name = "";

    try {
			Scanner scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {

        String[] strs = scanner.nextLine().split(" ", 2);
        String imageLoc = strs[0];
        String text = strs[1];

        if(!imageLoc.substring(0, 1).equals(">")){
          // add item to top level
          topLevelCategory.addItem(imageLoc, text);
          // create a new category
          categories.set(text, new AACCategory(text));
          // set current category to it 
          try {
           currentCategory = categories.get(text); 
          } catch (Exception e) {
            //
          }
        }
        else{
          // add item to current category
          currentCategory.addItem(imageLoc.substring(1), text);
        }
			}

      currentCategory = topLevelCategory;
      //System.err.println("current category: " + currentCategory.aa.toString());
      //System.err.println("top level category: " + topLevelCategory.aa.toString());
      //System.err.println("categories " + categories.toString());
      //System.out.println("name: " + currentCategory.name);

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    
  } // AACMappings(filename)

  /**
   * Adds the mapping to the current category (or the default category if that is the current category)
   * 
   * @param imageLoc the location of the image
   * @param text the text associated with the image
   */
  public void add(String imageLoc, String text){

    if(this.currentCategory.name.equals("")){
      this.topLevelCategory.addItem(imageLoc, text);
      this.categories.set(text, new AACCategory(text));
    }
    else{
      this.currentCategory.addItem(imageLoc, text);
    }
    System.err.println("current size: " + currentCategory.aa.size());
    System.err.println("top level size: " + topLevelCategory.aa.size());
    System.err.println("categories size: " + categories.size()); 
  } // add(String, String)

  /**
   * Gets the current category
   * 
   * @return returns the current category or the empty string if on the default category
   */
  public String getCurrentCategory(){
    return this.currentCategory.name;
  } // getCurrentCategory()

  /**
   * Provides an array of all the images in the current category
   * 
   * @return the array of images in the current category
   */
  public String[] getImageLocs(){
    // if(this.currentCategory.getImages() == null){
    //   throw new Exception("No images in the category");
    // }

    // try{
    //   if(this.currentCategory.name.equals("")){
    //   return topLevelCategory.getImages();
    // }
    // return this.currentCategory.getImages();
    // } catch (Exception e){
    //   return new String[5];
    // }

    // try{
    //   if(this.currentCategory.name.equals("")){
    //     return this.topLevelCategory.getImages();
    //   }
    //   else{
    //     return this.currentCategory.getImages();
    //   }
    // } catch(Exception e) {
    //   return new String[] {"img/food/icons8-strawberry-96.png"};
    // }

    try {
      return this.currentCategory.getImages();
    } catch (Exception e) {
       return new String[] {"img/food/icons8-strawberry-96.png"};
    }
  } // getImageLocs()

  /**
   * Given the image location selected, it determines the associated text with the image. 
   * If the image provided is a category, it also updates the AAC's current category to be 
   * the category associated with that image
   * 
   * @param imageLoc the location where the image is stored
   * @return returns the text associated with the current image
   */
  public String getText(String imageLoc){
    // try{
    //   return this.currentCategory.getText(imageLoc);
    // } catch (Exception e){
    //   return "";
    // } 
    System.err.println("hiiiiiii");
    try {
      //System.err.println("1");
      
      // this is where it breaks
      //currentCategory = categories.get(imageLoc);

      //System.err.println("2");
      //System.err.println("current category: " + currentCategory.name);
      //return currentCategory.getText(imageLoc);
      System.err.println("1");
      System.err.println("categories: " + categories.toString());
      currentCategory = categories.get("food");
      System.err.println("2");
      return currentCategory.getText("img/food/plate.png");
    
    } catch (Exception e) {
      System.err.println("oops");
      return "getText broke";
    }
  } // getText(String)


  /* Sam said that the underlying code doesn't use isCategory */

  /**
   * Determines if the image represents a category or text to speak
   * 
   * @param imageLoc the location where the image is stored
   * @return true if the image represents a category, false if the image represents text to speak
   */
  public boolean isCategory(String imageLoc){
    //return false; // STUB
    if(topLevelCategory.aa.hasKey(imageLoc)){
      return true;
    }
    return false;
  } // isCategory(String)

  /**
   * Resets the current category of the AAC back to the default category
   */
  public void reset(){
    try{
      //this.currentCategory = categories.get("");
      currentCategory = topLevelCategory;
    } catch (Exception e){
      //
    }
  } //reset()

  /**
   * Writes the ACC mappings stored to a file.
   * 
   * @param filename the name of the file to write the AAC mapping to
   */
  public void writeToFile(String filename){
    // stub
  } // writeToFile (String)

} // class AACMappings