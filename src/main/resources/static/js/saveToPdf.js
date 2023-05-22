const element = document.getElementById('invoice');
let buttonBlock = document.getElementById('saveButtonBlock');
let buttonToSave = document.getElementById('saveToPdf');


function generatePDF() {
    let opt = {
        margin: 0.1,
        filename: "cv.pdf",
        image: {type: "jpeg", quality: 0.8},
        enableLinks: true,
        jsPDF: {format: "A4", orientation: "landscape"}
    }
    html2pdf()
        .from(element)
        .set(opt)
        .save();
}

buttonToSave.onclick = generatePDF;

