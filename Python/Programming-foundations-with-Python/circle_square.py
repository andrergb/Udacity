import turtle

def draw_square(my_turtle):
    for num in range(0, 4):
        my_turtle.forward(100)
        my_turtle.right(90)

def draw_art():
    window = turtle.Screen()
    window.bgcolor("lightblue")

    #Create the turtle1 which draws several squares, forming a circle
    turtle1 = turtle.Turtle()
    turtle1.shape("turtle")
    turtle1.color("darkblue")
    turtle1.speed(10)

    for i in range(1, 37):
        draw_square(turtle1)
        turtle1.right(10)

    window.exitonclick()

draw_art()
