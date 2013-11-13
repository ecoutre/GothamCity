package Gui;
   import java.awt.event.*;
   import javax.swing.*;
   import java.awt.*;
   import java.awt.Color;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
    public class Map
   {
      ArrayList<Obstacle> obst = new ArrayList<Obstacle>();
      ArrayList<Rectangle> cords = new ArrayList<Rectangle>();
      ArrayList<Door> door = new ArrayList<Door>();
      String loc = " ";
      int xCord, yCord;
      int temp;
      
      public Map()
      {
    	 temp = 0;
         generate();
      }
       public Map(int t)
      {
    	 temp = t;
         generate();
      }

       public ArrayList<Obstacle> getObs()
      {
         return obst;
      }
       public ArrayList<Door> getDoors()
      {
         return door;
      }
       
       public void generate()
      {
         obst.clear();
         door.clear();
         
         if(temp==1){
        	 obst.add(new Obstacle(0,0, 400, 20));
             obst.add(new Obstacle(0,0, 20, 400));
             obst.add(new Obstacle(180, 180, 10, 50));
             obst.add(new Obstacle(180, 180, 50, 10));
         }
         else if(temp==2){
        	 
         }
         else if(temp==3){
        	 
         }
         else if(temp==4){
        	 
         }
         else if(temp==5){
        	 
         }
         
         
         
           /* if(y==0)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
                  obst.add(new Obstacle(0,0, 20, 400));
                  obst.add(new Obstacle(180, 180, 10, 50));
                  obst.add(new Obstacle(180, 180, 50, 10));
                  door.add(new Door(20,20));
               }
               if(x==1)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
               
               }
               if(x==2)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
               }
               if(x==3)
               {
                  obst.add(new Obstacle(0,0, 400, 20));
                  obst.add(new Obstacle(375,0,10, 400));
               }	
            }
            if(y ==1)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 20, 400));
               }
               if(x==1)
               {
                  obst.add(new Obstacle(190, 0, 20, 185));
                  obst.add(new Obstacle(190, 215, 20, 185));
               }
               if(x==2)
               {
                  obst.add(new Obstacle(190, 0, 20, 185));
                  obst.add(new Obstacle(190, 215, 20, 185));
               }
               if(x==3)
               {
                  obst.add(new Obstacle(375,0,10, 400));
               }
            }
            if(y==2)
            {
               if(x==0)
               {
                  obst.add(new Obstacle(0,0, 20, 400));
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
               if(x==1)
               {
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
               if(x==2)
               {
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
               if(x==3)
               {
                  obst.add(new Obstacle(375,0,10, 400));
                  obst.add(new Obstacle(0, 390, 400, 10));
               }
            }*/
         }
       
       public void paintAgents(Graphics g){
    	   //for Agent a : agents
    	   //gui.drawSelf()
       }
       
       public  void paintBackground(Graphics g)
      {
         g.setColor(Color.black);
         g.fillRect(0,0,400,450);
         g.setColor(Color.white);
         g.fillRect(0,0,400, 400);
         g.setColor(Color.black);
      }
      
       public  void paintObstacles(Graphics g2)
      {
         for (int i = 0; i < obst.size(); i++) {
            g2.setColor(Color.black);
            Obstacle o = obst.get(i);
            g2.fillRect(o.getX(),o.getY(),o.getWidth(),o.getHeight());
         }
         for(int a = 0; a< door.size(); a++) {
            g2.setColor(Color.red);
            Door d = door.get(a);
            g2.fillRect(d.getX(), d.getY(), d.getWidth(), d.getHeight());
         }
      }
   }