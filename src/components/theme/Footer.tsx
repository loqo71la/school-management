import { socialLinks } from '../../config';

function Footer() {
  return (
    <footer className="pt-6 pb-1 text-center">
      <div className="flex justify-center gap-2">
        {socialLinks.map((social, index) => (
          <a
            key={index}
            target="_blank"
            href={social.link}
            rel="noopener noreferrer"
          >
            <social.icon className="w-5 h-5 fill-gray-400 hover:fill-sky-500" />
          </a>
        ))}
      </div>
      <p className="text-sm font-light mt-2 text-gray-400">
        © 2022
        <a
          href="https://www.loqo71la.dev"
          className="ml-1 font-medium hover:text-sky-500"
        >
          loqo71la
        </a>
      </p>
    </footer>
  );
}

export default Footer;
