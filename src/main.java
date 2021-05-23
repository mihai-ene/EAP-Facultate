import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
      Menu menu = new Menu();
      menu.initConection();
      menu.loadClases();
      menu.menu();
    }
}

