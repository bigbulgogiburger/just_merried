import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  scenarios: {
    feed_read: {
      executor: 'ramping-vus',
      stages: [
        { duration: '1m', target: 200 },
        { duration: '3m', target: 500 },
        { duration: '1m', target: 0 },
      ],
      gracefulRampDown: '30s',
    },
  },
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<450'],
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8082';

export default function () {
  const res = http.get(`${BASE_URL}/api/v1/community/feed?page=0&size=20`);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response has items': (r) => r.body && r.body.includes('data'),
  });

  sleep(1);
}
