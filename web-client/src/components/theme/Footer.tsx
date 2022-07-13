import { socialLinks } from "../../App.config";

function Footer() {
  return (
    <footer className="pt-12 pb-2 bg-black text-white text-center">
      <div className="flex justify-center gap-2">
        {socialLinks.map((social, index) => (
          <a
            key={index}
            href={social.link}
            aria-label={`follow me on ${social.name}`}
          >
            <svg
              role="img"
              viewBox="0 0 24 24"
              className="w-6 h-6 fill-gray-400 hover:fill-teal-600"
              xmlns="http://www.w3.org/2000/svg"
            >
              <title>{social.name}</title>
              <path d={social.path} />
            </svg>
          </a>
        ))}
      </div>
      <p className="text-sm mt-3 text-gray-400">
        © 2022
        <a
          href="https://www.loqo71la.dev"
          className="ml-1 font-semibold hover:text-teal-600"
        >
          loqo71la
        </a>
      </p>
    </footer>
  );
}

export default Footer;