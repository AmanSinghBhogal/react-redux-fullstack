/* eslint-disable no-useless-catch */
import axios from 'axios';

export class NoteService {

    BASE_URL;

    constructor() {
        this.BASE_URL = "http://localhost:8080";
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