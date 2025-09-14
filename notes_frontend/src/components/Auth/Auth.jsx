import React, { useState } from 'react';
import './Auth.css';
import { VscEye } from "react-icons/vsc";
import { VscEyeClosed } from "react-icons/vsc";
import userService from '../../api/UserService';
import { useDispatch, useSelector } from 'react-redux';
import { login, logout } from '../../reducers/users/UserSlice';
import { useNavigate } from 'react-router';

const Auth = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [SignUp, setSignUp] = useState(false);
    const [showPassword, setShowPassword] = useState(false)
    const [PersonalDetails, setPersonalDetails] = useState({
        username: '',
        password: '',
        email: ''
    });
    
    const isAuthenticated = useSelector(state => state.users.isAuthenticated);
    

    function func(){
        dispatch(logout());
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            if(SignUp){
                console.log("Register user called.");
                console.log(PersonalDetails);
                userService.registerUser(PersonalDetails)
                    .then((httpResponse) => {
                        console.log("httpReponse is: ");
                        console.log(httpResponse);
                        const userData = httpResponse?.data?.response?.data;
                        console.log(userData);
                        console.log(PersonalDetails.password);
                        if(httpResponse !== null && httpResponse !== undefined){
                            if(httpResponse.status == 200){
                                userData.password = PersonalDetails.password;
                                dispatch(login(PersonalDetails));
                                navigate("/");
                            }
                        }
                        else{
                            alert("You Dumb!!! check your credentials...");
                        }
                    })
                    .finally(() => {
                        console.log("User Registered Successfully.");
                    })
            } 
            else{
                console.log("Log In Called");
                userService.loginUser(PersonalDetails)
                    .then((httpResponse) => {
                        console.log("httpResponse is: ");
                        console.log(httpResponse);
                        let userData = httpResponse?.data?.response?.data;
                        console.log(`Unhashed Password is: ${PersonalDetails.password}`);
                        userData= {...userData, password: PersonalDetails.password}
                        console.log("Dispatching user: ");
                        console.log(userData);
                        if(httpResponse !== null && httpResponse !== undefined ){
                            if(httpResponse.status == 200){
                                dispatch(login(userData));
                                navigate("/");
                            }
                        }
                        else{
                            alert("You Dumb!!! check your credentials...");
                        }
                    })
                    .finally(() => {
                        console.log("User Logged In");
                    })
            }
        }catch(err){
            console.log(err);
        }
    }


  return (
    <div id='login' class='authContainer'>
        {
            isAuthenticated? 
            <form onSubmit={func}>
                <div>
                    Already Logged in...
                    <button type='submit'>Sign Out</button>
                </div>
            </form>
            :
            <div class="login-container">
                <div class="login-title">
                    {
                        SignUp? "Register" : "Login"
                    }
                </div>
                <div class="login-form">
                    <form onSubmit={handleLogin}>
                            <div class="login-label">
                                <span>User Name:</span>
                                {
                                    SignUp?
                                        <span class="acc">Already Have an Account? &nbsp;
                                            <span class="login-switch" onClick={()=> setSignUp(false)}>
                                                Login
                                            </span>
                                        </span>
                                    :
                                        <span class='acc'>
                                            Need an Account? &nbsp;
                                            <span class="login-switch" onClick={()=> setSignUp(true)}>
                                                Register
                                            </span>
                                    </span>
                                }
                                
                            </div>
                            <input
                                required={SignUp && true}
                                type='text'
                                onChange={(e)=> {
                                    setPersonalDetails({
                                        ...PersonalDetails,
                                        username: e.target.value
                                    }) 
                                }}
                                placeholder="Enter Your Name">
                            </input>
                        {
                            SignUp && 
                            <>
                                <div class="login-label">
                                    <span>Email:</span>
                                </div>
                                <input type='email'
                                    autoComplete='email'
                                    onChange={(e)=> {
                                        setPersonalDetails({
                                            ...PersonalDetails, 
                                            email: e.target.value
                                        })
                                    }}
                                    placeholder='Enter Your Email'
                                    required
                                    />
                            </>
                        }
                        
                        <div class='login-label'>
                            <span>Password</span>
                        </div>

                        <div class='login-password'>
                            <input
                                type={showPassword? 'text': 'password'}
                                onChange={(e)=> {
                                    setPersonalDetails(
                                            {
                                                ...PersonalDetails,
                                                password: e.target.value
                                            }
                                        )
                                }}
                                placeholder='Enter your Password'
                                required
                            />
                            <div class="login-eye" onMouseDown={()=> {setShowPassword(true)}}
                                                    onMouseUp={()=> {setShowPassword(false)}}>
                                {
                                    showPassword ?
                                    <VscEyeClosed />
                                    :
                                    <VscEye />
                                }
                            </div>
                        </div>

                        <div class="login-submit">
                                <button type={'submit'}>
                                    {
                                        SignUp?
                                            'Sign Up'
                                            :
                                            'Log in'
                                    }
                                </button>
                        </div>
                    </form>

                </div>
        </div>
        }
    </div>
  )
}

export default Auth