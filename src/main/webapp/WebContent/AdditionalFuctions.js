function showAdditional(x) {

    if (x == 'car') {
        document.getElementById("carAdditional").style.display = 'block';
        document.getElementById("busAdditional").style.display = 'none';
        document.getElementById("truckAdditional").style.display = 'none';
    } else if (x == 'bus') {
        document.getElementById("busAdditional").style.display = 'block';
        document.getElementById("carAdditional").style.display = 'none';
        document.getElementById("truckAdditional").style.display = 'none';
    } else if (x == 'truck') {
        document.getElementById("truckAdditional").style.display = 'block';
        document.getElementById("busAdditional").style.display = 'none';
        document.getElementById("carAdditional").style.display = 'none';
    }
    return;
}