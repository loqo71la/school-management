import { GitHub, Grid1x2, Grid1x2Fill, HackerRank, Npm, People, PeopleFill, Telegram } from "@loqo71la/react-web-icons";

export const socialLinks = [
  { link: 'https://t.me/loqo71la', icon: Telegram },
  { link: 'https://github.com/loqo71la', icon: GitHub },
  { link: 'https://www.npmjs.com/~loqo71la', icon: Npm },
  { link: 'https://www.hackerrank.com/loqo71la', icon: HackerRank }
];

export const menus = [
  { name: 'Classes', path: '/classes', icon: Grid1x2, selectedIcon: Grid1x2Fill },
  { name: 'Students', path: '/students', icon: People, selectedIcon: PeopleFill }
]

export const api = {
  url: `${process.env.REACT_APP_SM_API_URL}/api/school-management`,
  error: 'The server encountered an internal error and was unable to complete your request.',
  expirationTime: 180000,
  sortBy: 'date',
  size: 50
};

export const genders = ['Male', 'Female', 'Non-binary', 'Transgender', 'Intersex', 'I prefer not to say']
