package com.practice.game.jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    private static Window window = null;
    private float r, g, b, a;
    private boolean fadeToBlack = false;
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {

        try {
            System.out.println("Hello LWJGL " + Version.getVersion() + "!");

            init();
            loop();
            // free memory
            glfwFreeCallbacks(glfwWindow);
            glfwDestroyWindow(glfwWindow);

            // terminate GLFW and the free the error callbacks
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public  void init() {
        //GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            throw  new IllegalStateException("Unable to initialize GLFW");
        }
        // configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, 0L, 0L);
        if (glfwWindow == 0L) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        // make the openGL context current
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync
        glfwSwapInterval(1);

        // make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadeToBlack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
                a = Math.max(a - 0.01f, 0);
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                 fadeToBlack = true;
            }
            glfwSwapBuffers(glfwWindow);
        }
    }
}
