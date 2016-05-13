package li.to_do_list;

/**
 * Created by Li on 5/13/16.
 */
public class Item {
    private int priority;  //stores priority;
    private String content; //stores to_do content;


    //constructor;
    public Item(int priority, String content){
        this.priority = priority;
        this.content = content;

    }

    //getters
    public int getPriority(){
        return priority;
    }

    public String getContent(){
        return content;
    }

    //setters
    public void setPriority(int priority){
        this.priority = priority;
    }

    public void setContent(String content){
        this.content = content;
    }
}
