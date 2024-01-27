'use strict'

import {selectFromList} from "./selectFromList.js";

/**
 *
 * @param ulContent
 * @param dictionaryList
 */
export function fillLines(ulContent, dictionaryList) {
    let newLine;
    dictionaryList.forEach(elem => {
        newLine = document.createElement('li');
        newLine.onclick = async function (event) {
            event.stopPropagation();
            await selectFromList(event.target);
        };
        newLine.dataset.value = elem['id'];
        newLine.textContent = elem['publicName'];
        ulContent.appendChild(newLine);
    });
}