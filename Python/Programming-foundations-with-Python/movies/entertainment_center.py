import media
import fresh_tomatoes

toy_story = media.Movie(
    "Toy Story",
    60,
    "A story of a boy and his toys that come to life",
    "https://upload.wikimedia.org/wikipedia/en/1/13/Toy_Story.jpg",
    "https://www.youtube.com/watch?v=KYz2wyBy3kc")

avatar = media.Movie(
    "Avatar",
    61,
    "A marine on an alien planet",
    "https://upload.wikimedia.org/wikipedia/en/b/b0/Avatar-Teaser-Poster.jpg",
    "https://www.youtube.com/watch?v=5PSNL1qE6VY")

lotr1 = media.Movie(
    "The Lord of the Rings The Fellowship of the Ring",
    62,
    "My precious",
    "https://upload.wikimedia.org/wikipedia/en/9/9d/The_Lord_of_the_Rings_The_Fellowship_of_the_Ring_%282001%29_theatrical_poster.jpg",
    "https://www.youtube.com/watch?v=Pki6jbSbXIY")

matrix = media.Movie(
    "Matrix",
    63,
    "The cake is a lie",
    "https://upload.wikimedia.org/wikipedia/en/c/c1/The_Matrix_Poster.jpg",
    "https://www.youtube.com/watch?v=m8e-FF8MsqU")

up = media.Movie(
    "UP",
    64,
    "Padre baloeiro",
    "https://upload.wikimedia.org/wikipedia/en/0/05/Up_%282009_film%29.jpg",
    "https://www.youtube.com/watch?v=pkqzFUhGPJg")

inception = media.Movie(
    "Inception",
    65,
    "Padre baloeiro",
    "https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg",
    "https://www.youtube.com/watch?v=8hP9D6kZseM")

movies = [toy_story, avatar, lotr1, matrix, up, inception]
fresh_tomatoes.open_movies_page(movies) #open the web page

#print media.Movie.VALID_RATINGS
#print media.Movie.__doc__
#print media.Movie.__name__ #should print "Movie"
#print media.Movie.__module__ #should print "media"
