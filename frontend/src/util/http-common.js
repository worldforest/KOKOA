import axios from 'axios';

// axios 객체 생성
export default axios.create({
  baseURL: 'https://k3b104.p.ssafy.io/api',
  headers: {
    'Content-type': 'application/json',
  },
});
