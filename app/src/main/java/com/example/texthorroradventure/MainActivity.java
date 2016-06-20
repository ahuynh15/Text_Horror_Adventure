package com.example.texthorroradventure;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    TextView story, health, sanity;
    int state, rhp, rsa, hp = 100, sa = 100, silver = 0, gold = 0;
    final String HIDE = "hide";
    String inventoryText;
    Button button1, button2, button3, button4, inventory;
    ProgressBar healthBar, sanityBar;
    List<String> inventoryList;
    boolean showInv = false;
    boolean waste = false,
            skeleton = false,
            plant = false,
            search = false,
            candle = false,
            fear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        story = (TextView) findViewById(R.id.story);
        health = (TextView) findViewById(R.id.health);
        sanity = (TextView) findViewById(R.id.sanity);
        inventory = (Button) findViewById(R.id.inventory);
        inventory.setVisibility(Button.GONE);
        button1 = (Button) findViewById(R.id.start);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button2.setVisibility(Button.GONE);
        button3.setVisibility(Button.GONE);
        button4.setVisibility(Button.GONE);
        health.setText("");
        sanity.setText("");
        state = 0;

        process(story, true, false);
    }

    public void clicked(View view) {
        process(view, false, false);
    }

    public void displayInventory(View view) {
        if(!showInv) {
            inventoryText = "Gold Key " + gold + '\n' + "Silver Key " + silver + '\n';
            for(String item : inventoryList)
            {
                inventoryText = inventoryText + item + '\n';
            }
            setStoryText(inventoryText);
            showInv = true;
        } else {
            process(view, true, true);
            showInv = false;
        }
    }

    public boolean checkInventory(String string) {
        for(String item : inventoryList) {
            if( item.equals(string) )
                return true;
        }
        return false;
    }

    private void setHealth(int hp) {
        this.hp = hp;
        if( this.hp > 100) this.hp = 100;
        health.setVisibility(View.VISIBLE);
        health.setText("Health: " + this.hp + "%");
    }

    private void setSanity(int sa) {
        this.sa = sa;
        if( this.sa > 100) this.sa = 100;
        sanity.setVisibility(View.VISIBLE);
        sanity.setText("Sanity: " + this.sa + "%");
    }

    private void setStoryText(String text) {
        story.setText(text);
    }

    private void setButtonText(String text1, String text2, String text3, String text4) {
        //Button 1
        if(text1.equals(HIDE))
            button1.setVisibility(Button.GONE);
        else {
            button1.setVisibility(Button.VISIBLE);
            button1.setText(text1);
        }
        //Button 2
        if(text2.equals(HIDE))
            button2.setVisibility(Button.GONE);
        else {
            button2.setVisibility(Button.VISIBLE);
            button2.setText(text2);
        }
        //Button 3
        if(text3.equals(HIDE))
            button3.setVisibility(Button.GONE);
        else {
            button3.setVisibility(Button.VISIBLE);
            button3.setText(text3);
        }
        //Button 4
        if(text4.equals(HIDE)) {
            button4.setVisibility(Button.GONE);
        }
        else {
            button4.setVisibility(Button.VISIBLE);
            button4.setText(text4);
        }
    }

    public void nextState(View view, int one, int two, int three, int four) {
        switch (view.getId()) {
            case R.id.start:
                state = one;
                Log.e("TAG","b1");
                break;
            case R.id.button2:
                state = two;
                Log.e("TAG","b2" + state);
                break;
            case R.id.button3:
                state = three;
                Log.e("TAG","b3" + state);
                break;
            case R.id.button4:
                state = four;
                Log.e("TAG","b4" + state);
                break;
        }
    }

    private void process(View view, boolean fake, boolean inv) {
        //(!fake) nextState(view, int, int, int)
        //(fake) add a resource (health, sanity, key, etc)

        String one = HIDE, two = HIDE, three = HIDE;
        switch (state) {
            case 0:
                setStoryText("ACT I - ESCAPE");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                inventory.setVisibility(Button.VISIBLE);
                setHealth(40);
                setSanity(65);
                silver = 0;
                gold = 0;
                inventoryList = new ArrayList<String>();
                waste = false;
                skeleton = false;
                plant = false;
                search = false;
                candle = false;
                fear = false;
                if(!fake) nextState(view, 1,0,0,0);
                break;
            //ACT I
            case 1:
                if (fake && !inv) inventoryList.add("Blood-Stained T-Shirt");
                if (fake && !inv) inventoryList.add("Ripped Jeans");
                if (fake && !inv) inventoryList.add("Torn Picture of a Man and a Woman");
                setStoryText("You wake up in a dim candle-lit room. In front of you is a heavy wooden door.");
                setButtonText("Look Around", "Cower in Fear","Put out the Candle", "Move Towards the Door");
                setHealth(40);
                setSanity(65);
                if(!fake) nextState(view, 2,15,23,26);
                break;
            case 2:
                search = true;
                setStoryText("You move your eyes across the room, spotting a dusty waste container, a dead plant, and a skeleton lain against a wall.");
                setButtonText("Search the Container", "Search the Dead Plant", "Search the Skeleton", "Search Nothing");
                if(!fake) nextState(view, 3,10,11,14);
                break;
            case 3:
                waste = true;
                setStoryText("You begin to dig through the waste and find a rotten rat. (Sanity -5%)");
                if(fake && !inv) setSanity(this.sa-5);
                setButtonText("Continue Digging","Stop Digging",HIDE,HIDE);
                if(!fake) nextState(view, 4,8,0,0);
                break;
            case 4:
                setStoryText("You continue to dig and come across a small wooden box.");
                if( silver >= 1 ) {
                    setButtonText("Use Brute Force to Open the Box", "Use a Key to Open the Box", "Leave the Box", HIDE);
                    if(!fake) nextState(view, 5, 6, 7, 0);
                } else {
                    setButtonText("Use Brute Force to Open the Box", HIDE, "Leave the Box", HIDE);
                    if(!fake) nextState(view, 5, 6, 7, 0);
                }
                break;
            case 5:
                if(fake && !inv) gold++;
                setStoryText("Using your remaining strength, you get the box open. (Health -10%)(Gold Key +1)");
                if(fake && !inv) setHealth(this.hp-10);
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 6:
                if(fake && !inv) gold++;
                setStoryText("Using the silver key, you get the box open. (Silver Key -1)(Gold Key +1)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 7:
                setStoryText("You leave the box alone");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 8:
                setStoryText("You stop digging through the trash.");
                setButtonText("Continue",HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 9:
                one = HIDE;
                two = HIDE;
                three = HIDE;
                setStoryText("What next?");
                if (!waste) one = "Search the Container";
                if (!plant) two = "Search the Dead Plant";
                if (!skeleton) three = "Search the Skeleton";
                setButtonText(one, two, three, "Stop Searching");
                if(!fake) nextState(view, 3, 10, 11, 14);
                break;
            case 10:
                if (fake && !inv) inventoryList.add("Dead Rose");
                plant = true;
                setStoryText("You dig through the soil and come up with nothing, but a dead plant");
                setButtonText("Continue",HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 11:
                one = HIDE;
                if(this.sa <= 50) one = "Continue to rummage through the remains";
                skeleton = true;
                if(fake && !inv) silver++;
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You begin to brush away the cobwebs and dust from the bones. You notice something in the skull and reach for it, finding a small silver key (Sanity -10%)(Silver Key +1)");
                setButtonText("Pay Your Respects to the Skeleton", "Leave the skeleton", one, HIDE);
                if(!fake) nextState(view, 12,9,13,0);
                break;
            case 12:
                if(fake && !inv) setSanity(this.sa + 5);
                setStoryText("You stare at the remains and pay respect to the deceased. (Sanity +5%)");
                setButtonText("Leave", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 13:
                if (fake && !inv) inventoryList.add("Finger Bone");
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You experience a lapse of brief insanity and throw bones across the room as your tear apart the remains. Eventually, you put a finger bone in your pocket. (Sanity -10%)(Item: Finger Bone)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 9,0,0,0);
                break;
            case 14:
                one = HIDE;
                two = HIDE;
                three = HIDE;
                if (!search) one = "Look Around";
                if (!fear) two = "Cower in Fear";
                if (!candle && sa <= 50) three = "Put out the Candle";
                setStoryText("You return back to the center of the room. What now?");
                setButtonText(one, two, three, "Move Towards the Door");
                if(!fake) nextState(view, 2,15,23,26);
                break;
            case 15:
                fear = true;
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You lay down on the floor, and curl up, moaning and sobbing. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 17,16,0,0);
                break;
            case 16:
                if(fake && !inv) setSanity(this.sa + 5);
                setStoryText("You wipe off your tears and recompose yourself. (Sanity +5%)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if (!fake) nextState(view, 14,0,0,0);
                break;
            case 17:
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("Your moaning and sobbing increase in volume. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 18,16,0,0);
                break;
            case 18:
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You begin to quake and shiver. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 19,16,0,0);
                break;
            case 19:
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You think about your lost future. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 20,16,0,0);
                break;
            case 20:
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You hear otherworldly noises heading towards your location. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 21,16,0,0);
                break;
            case 21:
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("You moan in despair. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 22,16,0,0);
                break;
            case 22:
                if(fake && !inv) setSanity(this.sa - 10);
                setStoryText("And you give up on life. (Sanity -10%)");
                setButtonText("Continue to cower", "Snap out of it", HIDE, HIDE);
                if (!fake) nextState(view, 17,16,0,0);
                break;
            case 23:
                candle = true;
                if(fake && !inv) setSanity(this.sa-10);
                setStoryText("You put out the candle and sit in the dark in complete silence. Then, you begin to hear heavy footsteps above you. You can feel the door in front of you.");
                setButtonText("Continue to sit", "Reach for the door", HIDE, HIDE);
                if (!fake) nextState(view, 24,26,0,0);
                break;
            case 24:
                if(fake && !inv) setSanity(this.sa-10);
                setStoryText("You hear whispers coming from the other side of the door.");
                setButtonText("Continue to sit", "Reach for the door", HIDE, HIDE);
                if (!fake) nextState(view, 25,26,0,0);
                break;
            case 25:
                if(fake && !inv) setSanity(this.sa-10);
                setStoryText("*Dead Silence*");
                setButtonText("Reach for the door", HIDE, HIDE, HIDE);
                if (!fake) nextState(view, 26, 0, 0,0);
                break;
            case 26:
                one = HIDE;
                two = HIDE;
                if (checkInventory("Finger Bone")) one = "Attempt to pick the lock with the finger bone";
                if (gold >= 1) two = "Use a gold key to open the door";
                setStoryText("You move towards the door");
                setButtonText("Try to open the door", "Use brute strength to open the door",one,two);
                if (!fake) nextState(view, 27,29,31,33);
                break;
            case 27:
                setStoryText("You twist the knob, but the door will not budge.");
                setButtonText("Continue", "Leave the door alone", HIDE, HIDE);
                if (!fake) nextState(view, 28, 14, 0, 0);
                break;
            case 28:
                one = HIDE;
                two = HIDE;
                if (checkInventory("Finger Bone")) one = "Attempt to pick the lock with the finger bone";
                if (gold >= 1) two = "Use a gold key to open the door";
                setStoryText("The door remains locked");
                setButtonText("Try to open the door", "Use brute strength to open the door",one,two);
                if (!fake) nextState(view, 27,29,32,31);
                break;
            case 29:
                if(fake && !inv) setHealth(this.hp-15);
                setStoryText("A splinter of wood cut your arm, but the the door begins to crack!");
                setButtonText("Ram the door again", "Try something else", "Leave the door alone", HIDE);
                if (!fake) nextState(view, 30, 28, 14, 0);
                break;
            case 30:
                if(fake && !inv) setHealth(this.hp-15);
                setStoryText("You manage to break the door, ending up in a dark hallway.");
                setButtonText("Continue",HIDE,HIDE,HIDE);
                if (!fake) nextState(view, 33, 0, 0, 0);
            case 31:
                if(fake && !inv) gold--;
                setStoryText("You use golden key and get the door open. On the other side of the door is a desolate hallway.");
                setButtonText("Continue", HIDE,HIDE,HIDE);
                if (!fake) nextState(view, 33, 0,0,0);
            case 32:
                if(fake && !inv) inventoryList.remove("Finger Bone");
                setStoryText("You successfully open the door and enter a hallway lined with candles");
                setButtonText("Continue", HIDE,HIDE, HIDE);
                if (!fake) nextState(view, 33,0,0,0);
                break;
            case 33:
                setStoryText("ACT II - ENCOUNTER");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 100,0,0,0);
                break;
            //ACT II starts at 100
            case 100:
                setStoryText("After exiting the door, you find you can go left and right. Which way do you go?");
                setButtonText("Go Left", "Go Right", HIDE, HIDE);
                if(!fake) nextState(view, 101,109,0,0);
                break;
            case 101:
                setStoryText("You turn left, but you come across another split in the path. Left or straight?");
                setButtonText("Keep on going forward", "Go left again", HIDE, HIDE);
                if(!fake) nextState(view, 102,106,0,0);
                break;
            case 102:
                setStoryText("You keep on going and reach a room at the end of the hallway.");
                setButtonText("Turn back", "Open the door", HIDE, HIDE);
                if(!fake) nextState(view, 105, 103,0,0);
                break;
            case 103:
                if(fake && !inv) setSanity(this.sa-5);
                setStoryText("Opening the door leads into the kitchen. Dried blood covers the kitchen walls and rusted utensils are scattered on the counter tops. (Sanity -5%)");
                setButtonText("Search the kitchen", "Turn around", HIDE, HIDE);
                if(!fake) nextState(view,104,105,0,0);
                break;
            case 104:
                if(fake && !inv) inventoryList.add("Rusty Knife");
                if(fake && !inv) setHealth(this.hp-5);
                setStoryText("You search through the room, opening cabinets and shelves. Ouch! You receive a splinter, but you find a rusty knife. (Health -5%)");
                setButtonText("Head back", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 105,0,0,0);
                break;
            case 105:
                setStoryText("You head back to the split.");
                setButtonText("Go the other way", "Backtrack even more", HIDE, HIDE);
                if(!fake) nextState(view, 106,109,0,0);
                break;
            case 106:
                one = HIDE;
                if(checkInventory("Rusty Knife")) one = "Wield the knife and swing behind!";
                setStoryText("You make the turn, and reach a dead end. Suddenly, the candle go out... You hear rapid breathing behind you...");
                setButtonText("Turn around",one, HIDE, HIDE);
                if(!fake) nextState(view, 107,108,0,0);
                break;
            case 107:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("As you turn, an arm grabs your shoulder and viciously drags you into the darkness.");
                setButtonText(HIDE, HIDE, HIDE, HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 108:
                if(fake && !inv) inventoryList.remove("Rusty Knife");
                if(fake && !inv) inventoryList.add("Broken Rusty Knife");
                if(fake && !inv) setSanity(this.sa + 10);
                if(fake && !inv) setHealth(this.hp + 10);
                if(fake && !inv) silver++;
                setStoryText("You rapidly turn your body and plunge your knife into a shadowy figure, breaking the blade... The figure disperses into the shadow and drops a silver key. You oddly feel rejuvenated and more confident. (Silver Key +1)(Health +5%)(Sanity +5%)");
                setButtonText("Head back to the first turn", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 109,0,0,0);
                break;
            case 109:
                setStoryText("You go the right way, but you suddenly get the chills");
                setButtonText("Keep going", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 110,0,0,0);
                break;
            case 110:
                setStoryText("You come across a potted plant as you walk...");
                setButtonText("Search it", "Forget about it", HIDE, HIDE);
                if(!fake) nextState(view, 111,115,0,0);
                break;
            case 111:
                one = HIDE;
                if(sa <= 30) one = "Grab the spider and crush it in your hands!";
                if(fake && !inv) setSanity(this.sa - 10);
                if(fake && !inv) setHealth(this.hp - 10);
                setStoryText("As your hand approaches the plant, a spider jumps onto it and bites it. You feel an intense pain in your hand. (Health -10%)(Sanity -10%)");
                setButtonText("Leave the pot", "Knock over the pot", "Dig", one);
                if(!fake) nextState(view,115,112,113,114);
                break;
            case 112:
                if(fake && !inv) gold++;
                setStoryText("In a fit of anger, you kick the pot over, breaking it. You find a gold key in the rubble. (Gold Key +1)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 115, 0,0,0);
                break;
            case 113:
                if(fake && !inv) gold++;
                setStoryText("Being courageous or merely idiotic, you begin to dig, ignoring any danger; however, you do find a gold key. (Gold Key +1)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 115, 0,0,0);
                break;
            case 114:
                if (fake && !inv) inventoryList.remove("Blood-Stained T-Shirt");
                if (fake && !inv) inventoryList.add("Blood and Ooze Covered T-Shirt");
                if(fake && !inv) setSanity(this.sa-5);
                setStoryText("CRrrraaacccckkk! Digusted by the green ooze left by the spider, you frantically try to get rid of it and immediate leave the area. (Sanity -5%)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 115, 0,0,0);
                break;
            case 115:
                if(fake && !inv) setSanity(this.sa-5);
                if(fake && !inv) setHealth(this.hp-5);
                setStoryText("As you continue down the hallway...SUDDENLY SOMETHING GRABS ONTO YOUR LEG. THE LIGHT GO OUT!");
                setButtonText("RUN AWAY", "FIGHT BACK", HIDE, HIDE);
                if(!fake) nextState(view, 118, 116, 0,0);
                break;
            case 116:
                setStoryText("You try to fight back with all your might to beat this monster. You get your leg free, but it grabs both of your arms. It's futile.");
                setButtonText("Resist!", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 117, 0,0,0);
                break;
            case 117:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("More shadowly arms begin to grab your entire body. You feel paralyzed... Everything becomes darker as your are dragged into the darkness.");
                setButtonText(HIDE, HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 0, 0,0,0);
                break;
            case 118:
                setStoryText("You manage to free your leg, giving you the opportunity to run away.");
                setButtonText("Run!", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 119,0,0,0);
                break;
            case 119:
                setStoryText("You head loud screeches behind you... yet there is hope. You see a door!");
                setButtonText("Run in and close the door!", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 120,0,0,0);
                break;
            case 120:
                setStoryText("You run in and slam the door behind you! The lights come back on... and the noises stop...");
                setButtonText("Rest", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 121,0,0,0);
                break;
            case 121:
                if(fake && !inv) setSanity(this.sa+10);
                if(fake && !inv) setHealth(this.hp+10);
                setStoryText("You have some time to recompose your mind and tend to your wounds... (Sanity +10%)(Health +10%)");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 122, 0,0,0);
                break;
            case 122:
                setStoryText("You look around the room and spot a staircase.");
                setButtonText("Reach the heavens by going upstairs!", "Basement or bust!", HIDE, HIDE);
                if(!fake) nextState(view, 200, 300,0,0);
                break;
            //ACT 3A
            case 200:
                setStoryText("ACT III - ASCEND");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 201,0,0,0);
                break;
            case 201:
                setStoryText("After walking up the stairs, you explore the upper floor, eventually, finding a rope hanging from the ceiling.");
                setButtonText("Examine the rope", "Look around", HIDE, HIDE);
                if(!fake) nextState(view, 202,208,0,0);
                break;
            case 202:
                setStoryText("You look up and notice an outline in the ceiling. It must lead to the attic.");
                setButtonText("Slightly tug the rope", "PULL IT!", HIDE, HIDE);
                if(!fake) nextState(view, 203,206,0,0);
                break;
            case 203:
                setStoryText("The attic door moves downward.");
                setButtonText("Tug again", "Pull it all the way", HIDE, HIDE);
                if(!fake) nextState(view, 204, 206,0,0);
                break;
            case 204:
                setStoryText("The attic door moves downward even more.");
                setButtonText("Tug again", "Pull it all the way", HIDE, HIDE);
                if(!fake) nextState(view, 205, 206,0,0);
                break;
            case 205:
                setStoryText("The attic ladder comes out.");
                setButtonText("Head up the ladder", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 207, 0,0,0);
                break;
            case 206:
                if(fake && !inv) setHealth(this.hp-5);
                setStoryText("The attic ladder comes down quickly. Unable to avoid it, you get hit. (Health -5%)");
                setButtonText("Head up the ladder", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 207, 0,0,0);
                break;
            case 207:
                setStoryText("You find yourself in the attic, which is fairly lit compared to the other parts of the house. For some reason, you find yourself calmer here.");
                setButtonText("Continue", HIDE, HIDE,HIDE);
                if(!fake) nextState(view, 209, 0,0,0);
                break;
            case 208:
                setStoryText("After looking around, you still find nothing intriguing except the rope. You decide to pull it.");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 203, 0,0,0);
                break;
            case 209:
                setStoryText("There appears to be a window in the back, and boxes are stacked up along the wall.");
                setButtonText("Walk towards the window", "Search the boxes", HIDE, HIDE);
                if(!fake) nextState(view, 210,215,0,0);
                break;
            case 210:
                one = HIDE;
                two = HIDE;
                if(sa <= 20) two = "JUMP OUT... ITS YOUR ONLY CHANCE";
                if(gold >= 1) one = "Open the window with a golden key";
                setStoryText("You walk towards the window, but the window is locked.");
                setButtonText("Break and climb out the window", one,two, "Leave the window alone and search the boxes");
                if(!fake) nextState(view,211,212,214,215);
                break;
            case 211:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You manage to break open the window. All of a sudden, something pushes you...");
                setButtonText(HIDE,HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 0,0,0,0);
                break;
            case 212:
                if(fake && !inv) gold--;
                setStoryText("You successfully open the window. And climb down slowly from the 2nd floor.");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 213,0,0,0);
                break;
            case 213:
                setStoryText("As you reach the ground you run as far as you. You take one final look and see a shadowy figure waving...");
                setButtonText("YOU SURVIVED", "Restart", HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 214:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You crash through the window...");
                setButtonText(HIDE,HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 0,0,0,0);
                break;
            case 215:
                if(fake && !inv) inventoryList.add("Stuffed Doll");
                setStoryText("After searching the boxes you come across a stuffed doll covered in dust and a bag of expired candy.");
                setButtonText("Eat the candy","Leave it in the box", HIDE, HIDE);
                if(!fake) nextState(view, 216,217,0,0);
                break;
            case 216:
                if(fake && !inv) setHealth(this.hp+15);
                setStoryText("You feel energized...weird... (Health +15%)");
                setButtonText("Continue",HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 218,0,0,0);
                break;
            case 217:
                setStoryText("You toss the candy aside...");
                setButtonText("Continue",HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 218,0,0,0);
                break;
            case 218:
                setStoryText("As you turn around you see a abstract, shadowy figure blocking your path.");
                setButtonText("Fight back", "Run towards the window", HIDE, HIDE);
                if(!fake) nextState(view, 219, 220,0,0);
                break;
            case 219:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You attack the figure, but it has no effect on the figure. The figure opens its mouth and engulfs you...");
                setButtonText(HIDE, HIDE, HIDE, HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 220:
                one = HIDE;
                if(hp <= 30) two = "JUMP!";
                two = HIDE;
                if(hp > 30) two = "JUMP!";

                setStoryText("You dash towards the window and decide to jump--its your only choice. Either way your a goner...");
                setButtonText(one,two,HIDE,HIDE);
                if(!fake) nextState(view,221,222,0,0);
                break;
            case 221:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You entire life flashes by as you fall...");
                setButtonText(HIDE, HIDE, HIDE, HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 222:
                if(fake && !inv) setHealth(10);
                setStoryText("You jump out the window... and SURVIVE");
                setButtonText("YOU SURVIVED", "Restart", HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
                //ACT 3B
            case 300:
                setStoryText("ACT III - DESCEND");
                setButtonText("Continue", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 301,0,0,0);
                break;
            case 301:
                setStoryText("You go into the basement. Maybe there is a way out down here. You notice a wooden door, pile of debris, and a barred window.");
                setButtonText("Open the door", "Examine the barred window", "Search the debris", HIDE);
                if(!fake) nextState(view, 302,308,313,0);
                break;
            case 302:
                setStoryText("You walk towards the door. Something feels odd... When you touch the doorknob, you hear a creaking noise. You ignore it. It's your imagination...");
                setButtonText("OPEN IT", HIDE, HIDE,HIDE);
                if(!fake) nextState(view, 303,0,0,0);
                break;
            case 303:
                one = HIDE;
                two = HIDE;
                if( checkInventory("Rusty Knife")) {one = "Fight Back";} else {two = "Fight Back";}
                setStoryText("The swing open the door and see a large green figure standing in front of you.");
                setButtonText("Run away!", one, two, HIDE);
                if(!fake) nextState(view, 304,306,307,0);
                break;
            case 304:
                if(fake && !inv) setSanity(10);
                setStoryText("You freeze in fear...The swamp monster stares into your eyes, and opens his mouth. You can see his sharp teeth, and fresh human flesh dangling off his mouth.");
                setButtonText("Continue",HIDE,HIDE,HIDE);
                if(!fake) nextState(view, 305,0,0,0);
                break;
            case 305:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You close your eyes, hoping that it is all a dream. You open them back up to see the monster swinging his big claws toward your face. You have lost hope, there are no more options.");
                setButtonText(HIDE,HIDE,HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 306:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You swing your fists at the figure's face. You slam your fist right across the monster's face. But, it did nothing. The monster just looks back at you and swing his big claws toward your face.");
                setButtonText(HIDE,HIDE,HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 307:
                if(fake && !inv) gold++;
                setStoryText("You stab the figure in the face. The figure instantly dissipates and leaves behind a gold key.(Gold Key +1)");
                setButtonText("Examine the window", "Search the debris", HIDE, HIDE);
                if(!fake) nextState(view, 308,313,0,0);
                break;
            case 308:
                one = HIDE;
                two = HIDE;
                if(gold>=1) one = "Use a gold key";
                if(silver>=1) two= "Use a silver key";
                setStoryText("You walk to the barred windows. You find that the bars are made of iron, making it extremely difficult to get past them. You see that there's a lock on the bars.");
                setButtonText("Search the debris nearby", one, two, HIDE);
                if(!fake) nextState(view, 313, 309, 311, 0);
                break;
            case 309:
                if(fake && !inv) gold--;
                setStoryText("You put the gold key inside the lock. You turn the key and hear a click. The lock falls off, and the bars begin to move.");
                setButtonText("Continue", HIDE, HIDE,HIDE);
                if(!fake) nextState(view, 310,0,0,0);
                break;
            case 310:
                setStoryText("You open the window, and lift yourself from the ground. You reach your hand out to feel the ground outside. You pull yourself out of the basement. You have successfully escaped!");
                setButtonText("YOU ESCAPED", "Restart", HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 311:
                if(fake && !inv) setHealth(10);
                if(fake && !inv) silver--;
                setStoryText("You try to put the silver key inside the lock, but it does not fit. As you turn back in disappointment, you hear a growl. You feel a piercing sensation in your neck...");
                setButtonText("Contin...", HIDE,HIDE,HIDE);
                if(!fake) nextState(view,312,0,0,0);
                break;
            case 312:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You become paralyzed and fall onto the ground... As your eyes begin to close, you hear footsteps...");
                setButtonText(HIDE,HIDE,HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 313:
                setStoryText("You move towards the debris.");
                setButtonText("Move everything aside", "Search the pile", HIDE, HIDE);
                if(!fake) nextState(view, 314, 315,0,0);
                break;
            case 314:
                setStoryText("You move the debris aside and find a tunnel. You think to yourself, 'This is my only option...'. So, you traverse the tunnel system...");
                setButtonText("YOU ESCAPED?", "Restart", HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
            case 315:
                setStoryText("As you search through the debris you some odd figurine...");
                setButtonText("Continue?", HIDE, HIDE, HIDE);
                if(!fake) nextState(view,316,0,0,0);
                break;
            case 316:
                setStoryText("A powder disperses from the figurine as you pick it up...You begin to get drowsy...");
                setButtonText("Throw it awa...", HIDE, HIDE, HIDE);
                if(!fake) nextState(view, 317,0,0,0);
                break;
            case 317:
                if(fake && !inv) setHealth(this.hp-100);
                setStoryText("You become dizzy and collapse onto the ground... As your eyes begin to close, you hear footsteps...");
                setButtonText(HIDE,HIDE,HIDE,HIDE);
                if(!fake) nextState(view,0,0,0,0);
                break;
        }
        //ALWAYS REFRESH
        if( sa <= 0 ) {
            sanity.setText("Sanity: 0%");
            setButtonText("You Lose Your Mind!", "GAME", "OVER", "Restart");
            nextState(view, 0,0,0,0);
            inventory.setVisibility(Button.GONE);
        }
        if( hp <= 0 ) {
            health.setText("Health: 0%");
            setButtonText("Your Life Has Ended!", "GAME", "OVER", "Restart");
            nextState(view, 0,0,0,0);
            inventory.setVisibility(Button.GONE);
        }
        if(!fake) process(view, true, false);
    }
}
