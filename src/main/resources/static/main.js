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