import turtle

def draw_square(my_turtle):
    for num in range(0, 4):
        my_turtle.forward(100)
        my_turtle.right(90)

def draw_circle(my_turtle):
    my_turtle.circle(100)

def draw_triangle(my_turtle):
    for num in range(0, 3):
        my_turtle.forward(100)
        my_turtle.left(120)

def draw_art():
    window = turtle.Screen()
    window.bgcolor("lightblue")

    #Create the turtle1 which draws a square
    turtle1 = turtle.Turtle()
    turtle1.shape("turtle")
    turtle1.color("darkblue")
    turtle1.speed(2)
    draw_square(turtle1)

    #Create the turtle2 which draws a circle
    turtle2 = turtle.Turtle()
    turtle2.shape("arrow")
    turtle2.color("red")
    turtle2.speed(2)
    draw_circle(turtle2)

    #Create the turtle3 which  draws a triangle
    turtle3 = turtle.Turtle()
    turtle3.shape("classic")
    turtle3.color("yellow")
    turtle3.speed(2)
    draw_triangle(turtle3)

    window.exitonclick()

draw_art()
