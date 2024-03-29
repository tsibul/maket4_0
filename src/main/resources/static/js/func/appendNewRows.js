'use strict'

import {typeDict} from "./typeDict.js";
import {fetchJsonData} from "./fetchJsonData.js";
import {fillNewRow} from "./fillNewRow.js";

/**
 * Append 20 rows after current row fetch from db
 * fetch url of dictionary_json from dictionary.py
 * @param rowCurrent
 * @param blockContent
 * @param searchString
 * @param shDeleted
 * @returns {Promise<*[]>}
 */
export async function appendNewRows(rowCurrent, blockContent, searchString, shDeleted) {
    const lastRecord = rowCurrent.dataset.last
    delete rowCurrent.dataset.last;
    let newRow;
    const rowCopy = blockContent.querySelector('.dict-block__row_hidden');
    const dictType = typeDict(rowCurrent);
    const jsonUrl = `/next_20/${dictType}/${lastRecord}/${searchString}/${shDeleted}`;
    const nextRecords = await fetchJsonData(jsonUrl);
    let i = 0;
    const newRows = [];
    for (const record of nextRecords) {
        i++;
        newRow = rowCopy.cloneNode(true);
        await fillNewRow(record, i, newRow);
        newRow.classList.remove('dict-block__row_hidden');
        if (i === 20) {
            newRow.dataset.last = (Number.parseInt(lastRecord) + 20).toString();
        }
        blockContent.appendChild(newRow);
        newRows.push(newRow);
    }
    return newRows;
}