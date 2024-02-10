//============================ 배너js=============================
document.addEventListener('DOMContentLoaded', function () {
    // Get the banner container
    var bannerContainer = document.getElementById('bannerContainer');

    // Get all banner items
    var bannerItems = bannerContainer.getElementsByTagName('div');

    // Set the index for the current active banner item
    var currentIndex = 0;

    // Function to show the next banner item
    function showNextBanner() {
        bannerItems[currentIndex].style.display = 'none';
        currentIndex = (currentIndex + 1) % bannerItems.length;
        bannerItems[currentIndex].style.display = 'block';
    }

    // Hide all banner items except the first one initially
    for (var i = 1; i < bannerItems.length; i++) {
        bannerItems[i].style.display = 'none';
    }

    // Set an interval to automatically show the next banner item every 3000 milliseconds (3 seconds)
    var slideInterval = setInterval(showNextBanner, 3000);

    // Stop the automatic slideshow when the user hovers over the banner container
    bannerContainer.addEventListener('mouseenter', function () {
        clearInterval(slideInterval);
    });

    // Resume the automatic slideshow when the user leaves the banner container
    bannerContainer.addEventListener('mouseleave', function () {
        slideInterval = setInterval(showNextBanner, 3000);
    });
});
//=============================여기까지 배너js

//===============================카드버튼js================================================
var page = 1;
var perPage = 4;
var size = 202 + 35 * 2;

var right = document.querySelector('.right');
right.addEventListener("click", () => {
    page += 1;
    updateCarousel();
});

var left = document.querySelector('.left');
left.addEventListener("click", () => {
    if (page > 1) {
        page -= 1;
        updateCarousel();
    }
});

function updateCarousel() {
    var carousel = document.querySelector('.list');
    var slideWidth = (size * (perPage * -1) * (page - 1));
    carousel.style.transform = `translateX(${slideWidth}px)`;

    // Hide cards that are not in the current view
    var cards = document.querySelectorAll('.card');
    cards.forEach((card, index) => {
        var cardIndex = (page - 1) * perPage + index;
        if (cardIndex >= perPage * page || cardIndex < perPage * (page - 1)) {
            card.style.display = 'none'; // Hide card by setting display to 'none'
        } else {
            card.style.display = 'block'; // Show card by setting display to 'block'
        }
    });
}

// Initial update to show the correct cards on page load
updateCarousel();
//=============================여기까지 카드버튼js==========================================
