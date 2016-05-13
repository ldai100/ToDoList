package li.to_do_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private Button enterButton;
    public final static String EXTRA_MESSAGE = "com.li.to_do_list.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        etNewItem = (EditText) findViewById(R.id.etNewItem);
        enterButton = (Button) findViewById(R.id.buttonAddItem);
        //make button and edit text initially invisible;
        etNewItem.setVisibility(View.GONE);
        enterButton.setVisibility(View.GONE);


        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);


        //adding action listeners;
        setupListViewListener();
        anotherListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }


    //starts new activity and shows whatever item is click;
    private void anotherListViewListener(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {

                        String message = items.get(pos);
                        seeContent(message);


                    }
        });
    }


    //when clicking on action bar, edit text and button will appear to enter item;
    public void wantToAdd(){
        etNewItem.setVisibility(View.VISIBLE);
        enterButton.setVisibility(View.VISIBLE);

    }

    //starts a new activity only shows the content;
    private void seeContent(String message){
        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


    //adds item and make editText and button disappear;
    public void onAddItem(View v) {

        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        etNewItem.setVisibility(View.GONE);
        enterButton.setVisibility(View.GONE);
    }

    //reading from todo.txt file;
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    //write to todo.txt file;
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //shows action bar and menus;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }



    //actions defined for action bars;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                wantToAdd();
                return true;
            case R.id.action_remove:

                return true;
            case R.id.action_save:

                return true;
            case R.id.about:
                seeContent(this.getString(R.string.about_text));
                return true;
            case R.id.faq:
                seeContent(this.getString(R.string.faq_text));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
