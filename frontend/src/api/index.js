import axios from 'axios';

export function getProfiles(){
    return axios.get('/api/profiles');
}

export function getProfileOf(id){
    return axios.get('/api/profiles/'+id);
}

export function getMyProfile(){
    return axios.get('/api/profiles/self');
}

export function updateProfile(id, body){
    return axios.put('/api/profiles/'+id, body);
}

export function getStaticData(){
    return axios.get('/api/static-data');
}

export function getLocations(loc){
    return axios.get('/api/locations/'+loc);
}

export function logout(){
    return axios.get('/logout');
}

export function login(cred){
    return axios({
        method: 'post',
        url: '/login',
        headers:{'Content-Type': 'application/x-www-form-urlencoded'},
        withCredentials: true,
        data: `username=${cred.username}&password=${cred.password}`
    });
}