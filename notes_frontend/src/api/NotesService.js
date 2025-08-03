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

    async deleteNote(note, userData) {
        try{
            console.log("Selected note is:: "+note.id);
            console.log("Hitting URL: "+this.BASE_URL+ "/notes?id="+note?.id);
            const response = await axios({
                method: 'delete',
                url: this.BASE_URL+ "/notes?id="+note?.id,
                auth: {
                    username: userData.username,
                    password: userData.password
                }
            })

            if(response){
                return("Note Deleted")
            }else{
                return null;
            }

        }catch(error){
            console.log(error) ;
        }
    }

    async postNote(note, userData){
        try{
            console.log("Request body is:: ");
            console.log(note);
            console.log("Posting New Note");
            const newNote = await axios({
                method: 'post',
                url: this.BASE_URL+"/notes",
                auth: {
                    username: userData.username,
                    password: userData.password
                },
                data: note
            })

            if(newNote !== null){
                console.log("New Note::");
                console.log(newNote);
                return newNote;
            }else{
                return null;
            }

        }catch(error){
            console.log(error) ;
            return error;
        }
    }

}

const noteService = new NoteService();

export default noteService;