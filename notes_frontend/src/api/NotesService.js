/* eslint-disable no-useless-catch */
import axios from 'axios';
import { BASE_URL } from './Constants';

export class NoteService {

    BASE_URL;

    constructor() {
        this.BASE_URL = BASE_URL;
    }

    async getNotes() {
        try{
            const notes = await axios({
                            method: 'get',
                            url: this.BASE_URL+"/notes/fetchAll"
                        });
            if(notes){
                return notes;
            }else{
                return null;
            }
        } catch(error){
            throw error;
        }
    }

}

const noteService = new NoteService();

export default noteService;