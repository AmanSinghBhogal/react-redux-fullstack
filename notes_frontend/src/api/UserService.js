/* eslint-disable no-useless-catch */
import axios from 'axios';
import { BASE_URL } from './Constants';

export class UserService {

    BASE_URL;

    constructor() {
        this.BASE_URL = BASE_URL;
    }

    async registerUser(userData) {
        try{
            const userResponse = await axios({
                method: 'post',
                url: this.BASE_URL+"/users/register",
                data: userData
            });
            if(userResponse){
                localStorage.setItem('userData', JSON.stringify({...userResponse?.data?.response?.data}))
                return userResponse;
            }else{
                return null;
            }
        }catch(error){
            console.log(error);
        }
    }

    async loginUser(userData){
        try{
            const userResponse= await axios({
                method: 'post',
                url: this.BASE_URL+"/users",
                auth: {
                    username: userData.username,
                    password: userData.password
                },
                data: userData
  
            });
            if(userResponse){
                localStorage.setItem('userData', JSON.stringify({...userResponse?.data?.response?.data}))
                return userResponse;
            }
            else{
                return null;
            }
        }catch(error){
            console.log(error);
        }
    }
}

const userService = new UserService();

export default userService;
