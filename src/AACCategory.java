import structures.AssociativeArray;
import structures.KeyNotFoundException;
/**
 * Represents the mappings for a single category of items to be displayed.
 * 
 * @author Jayson Kunkel
 */
public class AACCategory {
  
  //String imagePath;
  String name;
  AssociativeArray<String, String> aa;

  /**
   * Creates a new empty category with the given name
   * 
   * @param name the name of the category
   */
  public AACCategory(String name){
    this.name = name;
  } // AAACategory (String)

  /**
   * Adds the mapping of the imageLoc to the text to the category
   * 
   * @param imageLoc the location of the image to add
   * @param text the text that image maps to
   */
  public void addItem(String imageLoc, String text){
    this.aa.set(imageLoc, text);
  } // addItem(String, String)

  /**
   * Returns the name of the category
   * 
   * @return the name of the category
   */
  public String getCategory(){
    return this.name;
  } // getCategory()

  /**
   * Returns an array of all the images in the category.
   * Throws an exception if there are no images in the category.
   * 
   * @return the array of image locations
   */
  public String[] getImages() throws Exception{
    if(this.aa.getKeys() == null){
      throw new Exception("No images in the category");
    }
    return this.aa.getKeys();
  } // getImages()

  /**
   * Returns the text associated with the given image loc in this category.
   * Throws a KeyNotFoundException if the given image does not appear.
   * 
   * @param imageLoc the location of the image
   * @return the text associated with the image
   */
  public String getText(String imageLoc) throws KeyNotFoundException{
    return this.aa.get(imageLoc);
  } // getText(String)

  /**
   * Determines if the provided images is stored in the category
   * 
   * @param imageLoc the location of the category
   * @return true if it is in the category, false otherwise
   */
  public boolean hasImage(String imageLoc){
    return this.aa.hasKey(imageLoc);
  } //hasImage(String)

} // class AACCategory