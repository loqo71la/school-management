import { Docker, GitHub, Grid1x2, Grid1x2Fill, HackerRank, Java, Npm, People, PeopleFill } from "@loqo71la/react-web-icons";

export const socialLinks = [
  { link: 'https://github.com/loqo71la', icon: GitHub, title: 'GitHub' },
  { link: 'https://central.sonatype.com/namespace/dev.loqo71la', icon: Java, title: 'Maven Central' },
  { link: 'https://hub.docker.com/u/loqo71la', icon: Docker, title: 'DockerHub' },
  { link: 'https://www.npmjs.com/~loqo71la', icon: Npm, title: 'npm' },
  { link: 'https://www.hackerrank.com/loqo71la', icon: HackerRank, title: 'HackerRank' }
];

export const menus = [
  { name: 'Classes', path: '/classes', icon: Grid1x2, selectedIcon: Grid1x2Fill },
  { name: 'Students', path: '/students', icon: People, selectedIcon: PeopleFill }
]

export const api = {
  url: import.meta.env.VITE_SM_API_URL || 'http://localhost:8080/api',
  auth: import.meta.env.VITE_SM_AUTH_URL || 'https://app.loqo71la.dev/auth?client_id=4r97jsiucp6sk1nddo37huydf1&response_type=code',
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
