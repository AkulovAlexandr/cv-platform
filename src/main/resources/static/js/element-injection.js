let addSkillBtn = document.getElementById("addSkill");
let skillsRangeBlock = document.getElementById("skillsBlock");

let addJobBtn = document.getElementById("addJob");
let jobBlock = document.getElementById("jobBlock");


let addEduBtn = document.getElementById("addEducation");
let eduBlock = document.getElementById("eduBlock");

let addContactBtn = document.getElementById("addContact");
let contactBlock = document.getElementById("contactBlock");

let skillsCounter = skillsRangeBlock.children.length + 1;
let jobsCounter = jobBlock.children.length + 1;
let eduCounter = eduBlock.children.length + 1;
let contactCounter = contactBlock.children.length + 1;

addSkillBtn.onclick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (skillsCounter <= 10) {
        skillsRangeBlock.insertAdjacentHTML('beforeEnd', `<div class="skillItem p-3 mb-3">
                                <label for="skillRange${skillsCounter}" class="form-label d-flex justify-content-between">
                                    <input type="text" class="form-control form-control-sm form-label" name="skillTitle${skillsCounter}" placeholder="Навык" required>
                                <button type="button" class="closeButton btn btn-sm btn-outline-danger">X</button>
                                </label>
                                <input name="skillRange${skillsCounter}" type="range" class="form-range" min="0" max="100" id="skillRange${skillsCounter}">
                            </div>`);
        skillsCounter++;
        addEventToCloseButtons();
    }
    if (skillsCounter > 10) {
        addSkillBtn.setAttribute(`disabled`, ``);
    }
}

addJobBtn.onclick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (jobsCounter <= 5) {
        jobBlock.insertAdjacentHTML('beforeEnd', `<div class="jobItem p-3 mb-3">
                                <label for="jobTitle${jobsCounter}" class="form-label d-flex justify-content-between">Компания <button type="button" class="closeButton btn btn-sm btn-outline-danger">X</button></label>
                                <input name="jobTitle${jobsCounter}" type="text" class="form-control mb-3" id="jobTitle${jobsCounter}"
                                       placeholder="Например: ОАО &#34;Рога и копыта&#34;" value="" required>
                                <div class="row">
                                    <div class="col-6">
                                        <label for="jobYearStart${jobsCounter}" class="form-label">C</label>
                                        <input name="jobYearStart${jobsCounter}" type="number" min="1900" max="2023" class="form-control mb-3" id="jobYearStart${jobsCounter}"
                                               placeholder="2020" value="" required>
                                    </div>
                                    <div class="col-6">
                                        <label for="jobYearEnd${jobsCounter}" class="form-label">По</label>
                                        <input name="jobYearEnd${jobsCounter}" type="number" min="1900" max="2023" class="form-control mb-3" id="jobYearEnd${jobsCounter}"
                                               placeholder="2023" value="" required>
                                    </div>
                                </div>
                                <label for="jobDescription${jobsCounter}" class="form-label">Опишите обязанности</label>
                                <textarea  name="jobDescription${jobsCounter}" maxlength="1000" class="form-control mb-3" id="jobDescription${jobsCounter}"
                                          placeholder="Например: &#34;Специалист по работе с клиентами. Эффективный менеджер в галстуке и рубашке. Выполнял планы продаж и в ус не дул&#34;"
                                          required></textarea>
                            </div>`);
        jobsCounter++;
        addEventToCloseButtons();
    }
    if (jobsCounter > 5) {
        addJobBtn.setAttribute(`disabled`, ``);
    }
}

addEduBtn.onclick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (eduCounter <= 5) {
        eduBlock.insertAdjacentHTML('beforeEnd', `<div class="eduItem p-3 mb-3">
                                <label for="eduTitle${eduCounter}" class="form-label d-flex justify-content-between">Учебное заведение <button type="button" class="closeButton btn btn-sm btn-outline-danger">X</button></label>
                                <input name="eduTitle${eduCounter}" type="text" class="form-control mb-3" id="eduTitle${eduCounter}"
                                       placeholder="Например: Разработка на JAVA от ООО &#34;Айти-ШАГ&#34;" value="" required>
                                <div class="row">
                                    <div class="col-6">
                                        <label for="eduYearStart${eduCounter}" class="form-label">C</label>
                                        <input name="eduYearStart${eduCounter}" type="number" min="1900" max="2023" class="form-control mb-3" id="eduYearStart${eduCounter}"
                                               placeholder="2020" value="" required>
                                    </div>
                                    <div class="col-6">
                                        <label for="eduYearEnd${eduCounter}" class="form-label">По</label>
                                        <input name="eduYearEnd${eduCounter}" type="number" min="1900" max="2023" class="form-control mb-3" id="eduYearEnd${eduCounter}"
                                               placeholder="2023" value="" required>
                                    </div>
                                </div>
                                <label for="eduDescription${eduCounter}" class="form-label">Описание</label>
                                <textarea name="eduDescription${eduCounter}" maxlength="1000" class="form-control mb-3" id="eduDescription${eduCounter}"
                                          placeholder="Опишите чему научились, какой диплом получили. Например: &#34;Научился программировать&#34;"
                                          required></textarea>
                            </div>`);
        eduCounter++;
        addEventToCloseButtons();
    }
    if (eduCounter > 5) {
        addEduBtn.setAttribute(`disabled`, ``);
    }
}

addContactBtn.onclick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (contactCounter <= 3) {
        contactBlock.insertAdjacentHTML('beforeEnd', `<div class="contactItem p-3 mb-3">
                                                                     <label for="contactData${contactCounter}" class="form-label d-flex justify-content-between">Контакт <button
                                                                         type="button" class="closeButton btn btn-sm btn-outline-danger">X</button></label>
                                                                     <input name="contactData${contactCounter}" type="text" class="form-control mb-3" id="contactData${contactCounter}"
                                                                         placeholder="Например: &#34;+375 44 700 00 00&#34; или &#34;mail@email.com&#34; или &#34;https://url.com&#34;"
                                                                         value="" required>
                                                                     <label for="contactType${contactCounter}" class="form-label">Тип контакта</label>
                                                                     <select name="contactType${contactCounter}" class="form-select" id="contactType${contactCounter}" required>
                                                                            <option value="">Выберите тип...</option>
                                                                            <option>Телефон</option>
                                                                            <option>Почта</option>
                                                                            <option>Ссылка</option>
                                                                     </select>
                                                                 </div>`);
        contactCounter++;
        addEventToCloseButtons();
    }
    if (contactCounter > 3) {
        addContactBtn.setAttribute(`disabled`, ``);
    }
}



let closeElement = function (e) {
    e.preventDefault();
    e.stopPropagation();
    let blockForDelete = this.parentElement.parentElement;
    blockForDelete.remove();
    switch (blockForDelete.className.split(' ').at(0)) {
        case "jobItem":
            addJobBtn.removeAttribute('disabled');
            jobsCounter--;
            break;
        case "eduItem":
            addEduBtn.removeAttribute('disabled');
            eduCounter--;
            break;
        case "skillItem":
            addSkillBtn.removeAttribute('disabled');
            skillsCounter--;
            break;
        case "contactItem":
            addContactBtn.removeAttribute('disabled');
            contactCounter--;
            break;
    }
}
let addEventToCloseButtons = function () {
    let closeButtons = document.getElementsByClassName("closeButton");
    if (closeButtons.length > 0) {
        for (let closeButton of closeButtons) {
            closeButton.onclick = closeElement;
        }
    }
}


addEventToCloseButtons();