import { GitHub, Grid1x2, Grid1x2Fill, HackerRank, Npm, People, PeopleFill } from "@loqo71la/react-web-icons";

export const socialLinks = [
  { link: 'https://github.com/loqo71la', icon: GitHub },
  { link: 'https://www.npmjs.com/~loqo71la', icon: Npm },
  { link: 'https://www.hackerrank.com/loqo71la', icon: HackerRank }
];

export const menus = [
  { name: 'Classes', path: '/classes', icon: Grid1x2, selectedIcon: Grid1x2Fill },
  { name: 'Students', path: '/students', icon: People, selectedIcon: PeopleFill }
]

export const api = {
  url: import.meta.env.VITE_SM_API_URL || '',
  auth: import.meta.env.VITE_SM_AUTH_URL || '',
  error: 'The server encountered an internal error and was unable to complete your request.',
  expirationTime: 180000,
  sortBy: 'date',
  size: 50
};

export const genders = ['Male', 'Female', 'Non-binary', 'Transgender', 'Intersex', 'I prefer not to say'];

export const location = {
  latitude: 51.505,
  longitude: -0.09,
}
